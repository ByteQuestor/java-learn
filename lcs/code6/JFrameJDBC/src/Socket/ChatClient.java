package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 读取用户输入并发送消息，同时等待接收服务器回复
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine())!= null) {
                out.println(message);
                // 尝试读取服务器回复的消息
                if (in.ready()) {
                    String serverMessage = in.readLine();
                    System.out.println("对方: " + serverMessage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}