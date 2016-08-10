package foxgem.aggregation;

import foxgem.Utils;
import foxgem.service.AlbumService;
import foxgem.service.BookService;
import foxgem.service.IdentityService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

import java.util.HashMap;
import java.util.Map;

public class FrontendGateway extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        JWTAuth jwtAuth = Utils.createAuthProvider(vertx);

        Router router = Router.router(vertx);

        router.route("/*").handler(BodyHandler.create());
        router.route("/*").handler(routingContext -> {
            String path = routingContext.normalisedPath();
            if (path.equals("/login")) {
                Map<String, String> form = new HashMap<>();
                HttpServerRequest request = routingContext.request();
                form.put("name", request.getParam("name"));
                form.put("password", request.getParam("password"));
                form.put("scope", "frontend");

                HttpServerResponse response = routingContext.response();
                Utils.post(IdentityService.loginPath(), form, result -> {
                    boolean exist = (Boolean) (new JsonObject(result).getMap().get("exist"));
                    if (exist) {
                        Map user = (Map) (new JsonObject(result).getMap().get("user"));
                        String key = (String) user.keySet().toArray()[0];

                        JsonObject payload = new JsonObject();
                        payload.put("sub", key);
                        payload.put("name", ((Map) user.get(key)).get("name"));
                        payload.put("admin", false);

                        JWTOptions options = new JWTOptions();
                        options.setExpiresInSeconds(200L);
                        routingContext.response().end(jwtAuth.generateToken(payload, options));
                    } else {
                        Utils.fireForbiddenResponse(routingContext.response());
                    }
                }, Utils.failHandler(response));
            } else if (path.equals("/readme")) {
                routingContext.response()
                        .putHeader("content-type", "text/plain")
                        .end("An example for Frontend API Gateway\n");
            } else {
                routingContext.next();
            }
        });

        router.route("/*").handler(JWTAuthHandler.create(jwtAuth));

        router.route("/summary").handler(routingContext -> {
            String userId = getUserId(routingContext);
            Map resultOfServices = new HashMap();

            Utils.get(BookService.summaryPath(userId), resultOfBooks -> {
                Map result = new JsonObject(resultOfBooks).getMap();
                resultOfServices.put("books", result.get(userId));
            }, Utils.failHandler(routingContext.response()));

            Utils.get(AlbumService.summaryPath(userId), resultOfAlbums -> {
                Map result = new JsonObject(resultOfAlbums).getMap();
                resultOfServices.put("albums", result.get(userId));
            }, Utils.failHandler(routingContext.response()));

            JsonObject result = new JsonObject();
            result.put(userId, resultOfServices);

            routingContext.response().putHeader("content-type", "text/json; charset=utf-8").end(result.toString());
        });

        server.requestHandler(router::accept).listen(8080);
    }

    private String getUserId(RoutingContext routingContext) {
        if (routingContext.user() != null) {
            JsonObject principle = routingContext.user().principal();
            return principle.getString("sub");
        }

        return null;
    }

}
