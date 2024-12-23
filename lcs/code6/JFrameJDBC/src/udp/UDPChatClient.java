package udp;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPChatClient {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    public UDPChatClient(String serverAddress, int serverPort) {
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getByName(serverAddress);
            this.serverPort = serverPort;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送消息
    public void sendMessage(String message) {
        if (!message.isEmpty()) {
            try {
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);  // 发送消息
                System.out.println("客户端: " + message);  // 显示消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 接收服务器消息
    public String receiveMessage() {
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);  // 接收数据报
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        UDPChatClient client = new UDPChatClient("localhost", 9876);

        Scanner scanner = new Scanner(System.in);

        // 发送消息的循环
        while (true) {
            String message = scanner.nextLine();
            if ("exit".equalsIgnoreCase(message)) {
                break;
            }
            client.sendMessage(message);  // 发送消息

            // 接收并显示服务器的回复
            String response = client.receiveMessage();
            if (response != null) {
                System.out.println("服务器: " + response);
            }
        }

        scanner.close();
    }
}
