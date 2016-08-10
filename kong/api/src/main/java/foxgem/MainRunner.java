package foxgem;

import io.vertx.core.Vertx;

public class MainRunner {

    public static void main(String[] args) {
        deployService("foxgem.aggregation.FrontendGateway");
        deployService("foxgem.aggregation.BackendGateway");
        deployService("foxgem.service.AlbumService");
        deployService("foxgem.service.BookService");
        deployService("foxgem.service.IdentityService");
    }

    private static void deployService(String service) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(service, result -> {
            if (result.succeeded()) {
                System.out.println(service + " is started ...");
            }
        });
    }

}

