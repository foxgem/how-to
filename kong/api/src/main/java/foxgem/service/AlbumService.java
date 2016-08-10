package foxgem.service;

import foxgem.AlbumStore;
import foxgem.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

public class AlbumService extends AbstractVerticle {

    private static final String BASE_PATH = "http://localhost:8083";
    private static final String SUMMARY_PATH = "/summary";

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route("/:userId/summary").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            String userId = request.getParam("userId");

            Map result = new HashMap();
            result.put(userId, AlbumStore.countByUser(userId));
            Utils.fireJsonResponse(routingContext.response(), result);
        });


        server.requestHandler(router::accept).listen(8083);
    }

    public static String summaryPath(String userId) {
        return BASE_PATH + "/" + userId + SUMMARY_PATH;
    }

}
