package tstxxy.icu.real_me;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
    Router router;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        router = Router.router(vertx);

        router.route("/hello").handler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Yuan Haomin!");
        });

        vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });

    }
}
