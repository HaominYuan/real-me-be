package tstxxy.icu.real_me;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

public class MainVerticle extends AbstractVerticle {
    Router router;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        router = Router.router(vertx);

        router.route().handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET));

        router.get("/hello").handler(ctx -> {
            var response = ctx.response();
            response.setChunked(true);
            response.putHeader("content-type", "text/plain");
            ctx.vertx().setTimer(1000, tid -> ctx.next());
        }).handler(ctx -> {
            var response = ctx.response();
            response.write("Hello from Yuan Haomin").end();
        });

        router.get("/new").handler(ctx -> {
            var response = ctx.response();
            response.setChunked(true);
            response.putHeader("content-type", "text/plain");
            ctx.vertx().setTimer(500, tid -> ctx.next());
        }).handler(ctx -> {
            var response = ctx.response();
            response.write("This is a new new page").end();
        });

        router.get("/block").blockingHandler(ctx -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.next();
        }).handler(ctx -> {
            var response = ctx.response();
            response.putHeader("content-type", "text/plain");
            response.end("This is a blocking response with 10s");
        });

        vertx.createHttpServer().requestHandler(router).listen(80, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 80");
            } else {
                startPromise.fail(http.cause());
            }
        });

    }
}
