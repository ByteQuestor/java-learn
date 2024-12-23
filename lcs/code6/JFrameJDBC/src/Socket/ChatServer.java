package Socket;

import java.io.*;
import java.net.*;

public class ChatServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // 创建并启动服务器
        new ServerUI();  // 创建GUI窗口

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务器已启动...");
            while (true) {
                new ClientHandler(serverSocket.accept()).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) {
                    // 服务器接收到消息后更新界面
                    ServerUI.updateChatArea("收到消息: " + message);
                    out.println(message);  // 直接将消息发回客户端
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
