package tcp;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.spi.VertxFactory;
import lombok.Data;

import java.util.Scanner;

/**
 * TCP 客户端
 * @author : THINK.zhuyanpeng
 * @date :  2018/12/31- 0:28
 **/
public class Client {
    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setMaxEventLoopExecuteTime(400000);
        vertxOptions.setMaxWorkerExecuteTime(400000);
        vertxOptions.setBlockedThreadCheckInterval(400000);
        VertxFactory factory = Vertx.factory;
        Vertx vertx = factory.vertx(vertxOptions);
        NetClientOptions clientOptions = new NetClientOptions();
        //设置连接时间10秒
        clientOptions.setConnectTimeout(10000);
        NetClient client = vertx.createNetClient(clientOptions);
        //此处指定的端口是客户端的端口
        client.connect(8085,"127.0.0.1",netSocketAsyncResult -> {
            if (netSocketAsyncResult.succeeded()){
                System.out.println("Starting.......");
                System.out.println("向服务器发送数据=============");
                new ClientSocket(netSocketAsyncResult.result());
                /*buffer.appendString("你好啊！");
                System.out.println("<=======================>");
                result.write(buffer);
                result.handler(buffer1 -> {
                    System.out.println(buffer1.toString());
                });*/
            }else{
                System.out.println("Error.......");
            }
        });

    }

}
@Data
class  ClientSocket implements Handler<Buffer> {
    private NetSocket socket;

    public ClientSocket(NetSocket socket) {
        this.socket = socket;
        write();
    }

    @Override
    public void handle(Buffer buffer) {
        System.out.println(buffer.toString());
    }

    public void write(){
        for (int i = 0; i < 10; i++) {
            Buffer buffer = Buffer.buffer("第"+i+"次发送！","UTF-8");
            socket.write(buffer);
            socket.handler(this::handle);
        }
    }
}
