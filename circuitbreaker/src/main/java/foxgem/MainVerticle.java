package foxgem;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

    MockSystem ms = new MockSystem();

    @Override
    public void start() {
        deployCBWatcher();
        createCircuitBreakerForSucceed();
        createCircuitBreakerForFail();
        createCircuitBreakerForMaySucceed();
    }

    void deployCBWatcher() {
        vertx.eventBus().consumer("vertx.circuit-breaker", message -> {
            JsonObject body = (JsonObject) message.body();
            System.out.format("~~~~~ Circuit Breaker Status: node=%s, name=%s, state=%s, failures=%d ~~~~~\n",
                    body.getString("node"), body.getString("name"), body.getString("state"), body.getInteger("failures"));
        });
    }

    void createCircuitBreakerForSucceed() {
        CircuitBreaker cbSucceed = CircuitBreaker.create("ms-cb-succeed", vertx,
                new CircuitBreakerOptions()
                        .setMaxFailures(3)
                        .setTimeout(2000));
        cbSucceed.execute(future -> {
            future.complete(ms.succeed());
        }).setHandler(result -> {
            if (result.succeeded()) {
                System.out.println("result of ms.succeed(): " + result.result());
            }
        });
    }

    void createCircuitBreakerForFail() {
        CircuitBreaker cbFail = CircuitBreaker.create("ms-cb-fail", vertx,
                new CircuitBreakerOptions()
                        .setMaxFailures(3)
                        .setTimeout(2000))
                .openHandler(v -> {
                    System.out.println("ms-cb-fail is opened!");
                });

        vertx.setPeriodic(5000, id -> {
            cbFail.execute(future -> {
                ms.fail();
                future.complete();
            });
        });
    }

    void createCircuitBreakerForMaySucceed() {
        CircuitBreaker cbMaySucceed = CircuitBreaker.create("ms-cb-maysucceed", vertx,
                new CircuitBreakerOptions()
                        .setMaxFailures(3)
                        .setTimeout(1000)
                        .setResetTimeout(5000)
                        .setFallbackOnFailure(true))
                .closeHandler(v -> {
                    System.out.println("ms-cb-maysucceed is closed!");
                })
                .openHandler(v -> {
                    System.out.println("ms-cb-maysucceed is opened!");
                })
                .fallback(v -> {
                    System.out.println("ms-cb-maysucceed: into fallout function, return false.");
                    return false;
                });

        vertx.setPeriodic(3000, id -> {
            // if not in executeBlocking, then timeout could not be detected by circuit breaker.
            vertx.executeBlocking(v -> {
                cbMaySucceed.execute(future -> {
                    future.complete(ms.maySucceed());
                });
            }, null);
        });
    }

}
