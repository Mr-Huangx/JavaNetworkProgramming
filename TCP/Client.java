import com.sun.xml.internal.bind.api.impl.NameConverter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9876);
        try(InputStream inputStream = socket.getInputStream()){
            try(OutputStream outputStream = socket.getOutputStream()){
                handle(inputStream, outputStream);
            }
        }
        socket.close();
        System.out.println("关闭连接");
    }

    private static void handle(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        Scanner sc = new Scanner(System.in);
        System.out.println("【服务器】 " + reader.readLine());
        while( true ){
            System.out.println(">>> ");
            String s = sc.nextLine(); //读取用户输入
            writer.write(s);
            writer.newLine();
            writer.flush();
            String response = reader.readLine();
            System.out.println("<<< " + response);
            if(response.equals("bye")){
                break;
            }
        }
    }
}


