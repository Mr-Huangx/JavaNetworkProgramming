import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket ds = new DatagramSocket(6666);
        while( true ){
            // 创建数据缓冲区
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            //监听并接收UDP数据包
            ds.receive(datagramPacket);

            String s = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength(), StandardCharsets.UTF_8);
            System.out.println("【客户端发来数据】：" + s);
            Thread.sleep(1000);//延迟一秒在返回报文

            //发送该数据
            byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
            datagramPacket.setData(data);
            ds.send(datagramPacket);
        }
    }
}
