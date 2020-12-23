package tstxxy.icu.real_me;

import com.sun.tools.jconsole.JConsoleContext;
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
            response.write("Hello from Yuan Haomin");
            response.end();
        });

        router.get("/login").handler(ctx -> {
            var response = ctx.response();
            response.setChunked(true);
            response.putHeader("content-type", "text/plain");
            String login = "tstxxy";
            String password = "qwer1234";
            System.out.println(ctx.request().params());
            var params = ctx.request().params();
            if (params.get(("username")).equals("tstxxy") && params.get("password").equals("qwer1234")) {
                response.write("Yes");
            } else {
                response.write("No");
            }
            response.end();

        });

        router.get("/new").handler(ctx -> {
            var response = ctx.response();
            response.setChunked(true);
            response.putHeader("content-type", "text/plain");
            ctx.vertx().setTimer(500, tid -> ctx.next());
        }).handler(ctx -> {
            var response = ctx.response();
            response.write("This is a new new page");
            response.end();
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
