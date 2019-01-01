package tcp;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.spi.VertxFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TCP 服务端
 * @author : THINK.zhuyanpeng
 * @date : 2018/12/30- 23:23
 **/
public class Demo3 {
    public static VertxFactory factory = Vertx.factory;
    public static void main(String[] args) {
        test1();
    }
    public static void test1(){
        Vertx vertx = factory.vertx();
        NetServerOptions netServerOptions = new NetServerOptions();
        netServerOptions.setPort(8085);
        netServerOptions.setHost("127.0.0.1");
        NetServer netServer = vertx.createNetServer(netServerOptions);
        netServer.connectHandler(new SocketService());
        netServer.listen(netServerAsyncResult -> {
            //服务开启成功时的回调
            if (netServerAsyncResult.succeeded()){
                System.out.println("server starting .......");
            }else{
                System.out.println("server error...........");
            }

        });
    }
}
class  SocketService implements  Handler<NetSocket>{
    private  NetSocket socket;
    @Override
    public void handle(NetSocket socket) {
        this.socket =socket;
        System.out.println("地址为:"+socket.localAddress()+"]的连接了\r\n");
        Data data = new Data(socket);
        data.write(String.format("感谢您的请求！%s\r\n",socket.localAddress()));
    }

    public void close(){
        socket.closeHandler(close->{
            System.out.println("地址为:"+socket.localAddress()+"]的退出了");
        });

    }
    @lombok.Data
    class  Data implements Handler<Buffer>{
        private String message;
        private NetSocket socket;

        public Data(NetSocket socket) {
            this.socket = socket;
            socket.handler(this);
        }

        @Override
        public void handle(Buffer buffer) {
            message = buffer.toString();
            System.out.println("接收到客户端的访问："+buffer.toString());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            message = String.format("亲爱的[%s]我已收到您发送的%s了，感谢您的请求！再见%s\n", socket.localAddress(), message, dateFormat.format(new Date()));
            write(message);
        }

        public void write(String message) {
            Buffer buffer = Buffer.buffer(message,"UTF-8");
            socket.write(buffer);

        }
    }
}