package udp;

import java.net.*;
import java.io.*;

public class UDPChatServer {
    private DatagramSocket socket;
    private int serverPort;
    private UDPChatServerUI ui;

    public UDPChatServer(int serverPort) {
        try {
            this.socket = new DatagramSocket(serverPort);
            this.serverPort = serverPort;
            this.ui = new UDPChatServerUI();  // 初始化 UI 界面
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 接收客户端的消息
    public void receiveMessage() {
        new Thread(() -> {
            try {
                while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);  // 接收数据报

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    ui.appendMessage("客户端: " + message);  // 更新 UI 显示客户端的消息

                    // 回复客户端
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    sendMessage("已收到: " + message, clientAddress, clientPort);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 发送消息到客户端
    private void sendMessage(String message, InetAddress clientAddress, int clientPort) {
        try {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);  // 发送消息
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UDPChatServer server = new UDPChatServer(9876);
        server.receiveMessage();  // 启动接收消息的线程
    }
}
