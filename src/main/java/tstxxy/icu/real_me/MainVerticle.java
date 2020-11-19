package tstxxy.icu.real_me;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
    Router router;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        router = Router.router(vertx);
        router.get("/hello").handler(ctx -> {
            var response = ctx.response();
            response.setChunked(true);
            response.putHeader("content-type", "text/plain");
            ctx.vertx().setTimer(1000, tid -> ctx.next());
        }).handler(ctx -> {
            var response = ctx.response();
            response.write("Hello from Yuan Haomin").end();
        });

        vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 80");
            } else {
                startPromise.fail(http.cause());
            }
        });

    }
}
