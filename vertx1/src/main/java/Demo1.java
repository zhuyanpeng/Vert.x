import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.VertxFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * demo1
 * @author : THINK.zhuyanpeng
 * @Description: :
 * @date :Create in  2018/12/30- 13:22
 * @Version: V1.0
 * @Modified By:
 **/
public class Demo1 {
    public static void main(String[] args) {
        test1();
        //test2();
    }
    public static void test1() {
        VertxFactory factory = Vertx.factory;
        factory.vertx().createHttpServer().requestHandler(req ->req.response().end("hello!world!")).listen(8080);
    }

    public static void test2() {
        VertxFactory factory = Vertx.factory;
        Vertx vertx = factory.vertx();
        HttpServer httpServer = vertx.createHttpServer().requestHandler(httpServerRequest -> {
            String path = httpServerRequest.path();
            int i = path.indexOf("/");
            path = path.substring(i+1);
            String file = "error";
            if (StringUtils.equals(path, "index")) {
                file = "index";
            } else if (StringUtils.equals(path, "helloworld")) {
                file = "helloworld";
            }
            httpServerRequest.response().sendFile("webs/" + file + ".html");
        });
        httpServer.listen(8080);
    }

    public static void  test3(){
        Vertx vertx =  Vertx.factory.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(request -> {
            handleGet(request);
        }).listen(8083);
    }


    public static void handleGet(HttpServerRequest request){
        String path = request.path();
        System.out.println(path);
        HttpServerResponse response = request.response();
        JsonObject object = new JsonObject();
        object.put("第一","张三");
        object.put("第二","李四");
        object.put("第三","王五");
        response.putHeader("content-type","application/json").end(object.toString());
    }
}
