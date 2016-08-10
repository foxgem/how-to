package foxgem.service;

import foxgem.IdentityStore;
import foxgem.Utils;
import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

public class IdentityService extends AbstractVerticle {

    private static final String BASE_PATH = "http://localhost:8081";
    private static final String USERS_PATH = "/users";
    private static final String LOGIN_PATH = "/login";

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route(HttpMethod.POST, LOGIN_PATH).handler(BodyHandler.create());
        router.route(HttpMethod.POST, LOGIN_PATH).handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            String name = request.getParam("name");
            String password = request.getParam("password");
            String scope = request.getParam("scope");

            if ("frontend".equals(scope)) {
                Map result = new HashMap();
                if (IdentityStore.getUser(name, password).isEmpty()) {
                    result.put("exist", false);
                } else {
                    result.put("exist", true);
                    result.put("user", IdentityStore.getUser(name, password));
                }
                Utils.fireJsonResponse(routingContext.response(), result);
            } else if ("backend".equals(scope)) {
                Map result = new HashMap();
                if (IdentityStore.rootExists(name, password)) {
                    result.put("exist", true);
                } else {
                    result.put("exist", false);
                }
                Utils.fireJsonResponse(routingContext.response(), result);
            } else {
                JsonObject result = new JsonObject();
                result.put("exist", false);
                routingContext.response().setStatusCode(403).end(result.encode());
            }
        });

        router.route(USERS_PATH).handler(routingContext -> {
            Utils.fireJsonResponse(routingContext.response(), IdentityStore.findAllUsers());
        });

        server.requestHandler(router::accept).listen(8081);
    }

    public static String usersPath() {
        return BASE_PATH + USERS_PATH;
    }

    public static String loginPath() {
        return BASE_PATH + LOGIN_PATH;
    }

}
