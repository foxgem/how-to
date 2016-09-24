package foxgem;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class Service extends AbstractVerticle {

    private String name;
    private int port;

    private Vertx vertx;
    private ServiceDiscovery discovery;

    public Service(String name, int port) {
        this.name = name;
        this.port = port;

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
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        httpServer.requestHandler(router::accept).listen(port, result -> {
            if (result.succeeded()) {
                System.out.println(String.format("Service %s is listening on port %d", name, port));
            }
        });

        router.route("/name").handler(routingContext -> {
            routingContext.response().setStatusCode(200);
            routingContext.response().end(String.format("My name is %s, I'm listening on port %d", name, port));
        });

        Record record = HttpEndpoint.createRecord(name, "localhost", port, "/name");
        discovery.publish(record, result -> {
            if (result.succeeded()) {
                System.out.println(String.format("Service %s(%d) is published successfully.", name, port));
            } else {
                result.cause().printStackTrace();
            }
        });
    }

    @Override
    public void stop() {
        discovery.close();
    }

}
