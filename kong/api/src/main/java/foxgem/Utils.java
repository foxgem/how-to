package foxgem;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class Utils {

    static OkHttpClient httpClient = new OkHttpClient();

    public static void fireJsonResponse(HttpServerResponse response, Map result) {
        JsonObject jsonObject = new JsonObject(result);
        response.putHeader("content-type", "text/json; charset=utf-8").end(jsonObject.toString());
    }

    public static void fire404(HttpServerResponse response) {
        response.setStatusCode(404).end();
    }

    public static void fire200(HttpServerResponse response) {
        response.setStatusCode(200).end();
    }

    public static void get(String url, Consumer<String> successHandler, Consumer<IOException> failHandler) {
        Request request = new Request.Builder().url(url).build();
        try {
            successHandler.accept(httpClient.newCall(request).execute().body().string());
        } catch (IOException e) {
            failHandler.accept(e);
        }
    }

    public static void post(String url, Map<String, String> form,
                            Consumer<String> successHandler, Consumer<IOException> failHandler) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        form.forEach((key, value) -> {
            bodyBuilder.add(key, value);
        });
        Request request = new Request.Builder().url(url).post(bodyBuilder.build()).build();
        try {
            successHandler.accept(httpClient.newCall(request).execute().body().string());
        } catch (IOException e) {
            failHandler.accept(e);
        }
    }


    public static JWTAuth createAuthProvider(Vertx vertx) {
        JsonObject config = new JsonObject().put("keyStore", new JsonObject()
                .put("path", "keystore.jceks")
                .put("type", "jceks")
                .put("password", "secret"));

        return JWTAuth.create(vertx, config);
    }

    public static Consumer<String> successHandler(HttpServerResponse response) {
        return result -> {
            response.putHeader("content-type", "text/json; charset=utf-8").end(result);
        };
    }

    public static Consumer<IOException> failHandler(HttpServerResponse response) {
        return exception -> {
            response.putHeader("content-type", "text/json; charset=utf-8");
            JsonObject jsonObject = new JsonObject();
            jsonObject.put("success", "false");
            jsonObject.put("errorMessage", exception.getMessage());
            response.end(jsonObject.toString());
        };
    }

    public static void fireForbiddenResponse(HttpServerResponse response) {
        response.setStatusCode(403).end();
    }

}
