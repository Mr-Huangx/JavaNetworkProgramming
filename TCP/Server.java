import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9876); // 监听指定端口
        System.out.println("server is running...");
        for (;;) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }
}

class Handler extends Thread {
    Socket sock;

    public Handler(Socket socket){
        this.sock = socket;
    }


    @Override
    public void run() {
        try(InputStream inputStream = this.sock.getInputStream()){
            try(OutputStream outputStream = this.sock.getOutputStream()){
                handle(inputStream, outputStream);
            }
        } catch (Exception e){
            try{
                this.sock.close();
            } catch (IOException ioe){

            }
            System.out.println("客户端断开连接");
        }
    }

    private void handle(InputStream inputStream, OutputStream outputStream) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        writer.write("hello \n");
        writer.flush();

        while( true ){
            String s = reader.readLine();
            System.out.println("【客户端】 " + s);
            if(s.equals("bye")){
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("ok: " + s + "\n");
            writer.flush();
        }
    }
}