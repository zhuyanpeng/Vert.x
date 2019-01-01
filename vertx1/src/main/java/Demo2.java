import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.spi.VertxFactory;

/**
 * web server 模块实现web服务器
 * @author : THINK.zhuyanpeng
 * @Description: :
 * @date :Create in  2018/12/30- 14:32
 * @Version: V1.0
 * @Modified By:
 **/
public class Demo2 {
   public static VertxFactory factory = Vertx.factory;
    public static void main(String[] args) {
        test2();
    }
    public static void  test1(){
        Vertx vertx =  factory.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(req ->{
            req.response().sendFile("webs"+req.path());
        });
        httpServer.listen(8083);
    }

    public static void  test2(){
        Vertx vertx =  factory.vertx();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest request) {
                request.response().sendFile("webs"+request.path());
            }
        });
        httpServer.listen(8083);
    }



}
