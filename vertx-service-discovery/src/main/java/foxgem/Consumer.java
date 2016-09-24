package foxgem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;

public class Consumer extends AbstractVerticle {

    private Vertx vertx;
    private ServiceDiscovery discovery;
    private String serviceName;

    public Consumer(String name) {
        this.serviceName = name;

        Vertx.clusteredVertx(new VertxOptions(), result -> {
            if (result.succeeded()) {
                vertx = result.result();
                discovery = ServiceDiscovery.create(vertx);
                vertx.deployVerticle(this);
            }
        });
    }

    @Override
    public void start() {
        vertx.setPeriodic(2000, tid -> consume());
    }

    @Override
    public void stop() {
        discovery.close();
    }

    private void consume() {
        discovery.getRecords(new JsonObject().put("name", serviceName), result -> {
            if (result.succeeded() && result.result() != null) {
                int index = (int) System.currentTimeMillis() % result.result().size();
                Record record = result.result().get(index);
                ServiceReference serviceReference = discovery.getReference(record);
                HttpClient httpClient = serviceReference.get();
                httpClient.getNow("/name", response -> {
                    response.bodyHandler(totalBuffer -> {
                        String resultFromService = totalBuffer.toString();
                        System.out.print(String.format("Consumer get result: %s\n", resultFromService));
                        serviceReference.release();
                    });
                });
            } else {
                result.cause().printStackTrace();
            }
        });
    }

}
