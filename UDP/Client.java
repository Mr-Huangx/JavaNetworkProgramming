import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.setSoTimeout(2000);

        //链接服务器和端口
        datagramSocket.connect(InetAddress.getByName("localhost"), 6666);

        //创建数据缓冲区
        byte[] data = "Hello ".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length);

        // 发送数据
        datagramSocket.send(packet);

        //接收数据
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        String response = new String(packet.getData(), packet.getOffset(), packet.getLength());
        System.out.println("【服务器返回结果】：" + response);

        datagramSocket.disconnect();
        datagramSocket.close();
    }
}
