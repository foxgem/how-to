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
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;

import java.util.HashMap;
import java.util.Map;

public class BackendGateway extends AbstractVerticle {

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
                form.put("scope", "backend");

                HttpServerResponse response = routingContext.response();
                Utils.post(IdentityService.loginPath(), form, result -> {
                    boolean exist = (Boolean) (new JsonObject(result).getMap().get("exist"));
                    if (exist) {
                        JsonObject payload = new JsonObject();
                        payload.put("sub", "root");

                        JWTOptions options = new JWTOptions();
                        routingContext.response().end(jwtAuth.generateToken(payload, options));
                    } else {
                        Utils.fireForbiddenResponse(routingContext.response());
                    }
                }, Utils.failHandler(response));
            } else if (path.equals("/readme")) {
                routingContext.response()
                        .putHeader("content-type", "text/plain")
                        .end("An example for Backend API Gateway\n");
            } else {
                routingContext.next();
            }
        });

        router.route("/*").handler(JWTAuthHandler.create(jwtAuth));

        router.route("/users").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            Utils.get(IdentityService.usersPath(), Utils.successHandler(response), Utils.failHandler(response));
        });

        router.route("/:userId/summary").handler(routingContext -> {
            String userId = routingContext.request().getParam("userId");
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

        server.requestHandler(router::accept).listen(8084);
    }

}
