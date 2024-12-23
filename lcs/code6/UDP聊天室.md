UDP聊天室

UDP客户端

```java
package udp;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class UDPChatClient extends JFrame {
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
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

        // 创建 UI
        setTitle("UDP 客户端");
        setLayout(new BorderLayout());

        // 消息区域
        messageArea = new JTextArea(15, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // 输入框和按钮
        inputField = new JTextField(30);
        sendButton = new JButton("发送");

        // 按钮事件
        sendButton.addActionListener(e -> sendMessage());

        // 创建底部面板，包含输入框和发送按钮
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(inputField);
        bottomPanel.add(sendButton);

        // 添加组件
        add(scrollPane, BorderLayout.CENTER);  // 消息区域
        add(bottomPanel, BorderLayout.SOUTH);  // 底部区域

        // 设置窗口基本属性
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);
    }

    // 发送消息
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            try {
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);  // 发送消息

                messageArea.append("客户端: " + message + "\n");  // 显示消息
                inputField.setText("");  // 清空输入框
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 接收服务器消息
    public void receiveMessage() {
        new Thread(() -> {
            try {
                while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);  // 接收数据报
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    messageArea.append("服务器: " + message + "\n");  // 显示服务器的消息
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        UDPChatClient clientUI = new UDPChatClient("localhost", 9876);
        clientUI.receiveMessage();  // 启动接收消息的线程
    }
}

```

UDP服务端

```java
package udp;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class UDPChatServer extends JFrame {
    private JTextArea messageArea;
    private DatagramSocket socket;
    private int serverPort;

    public UDPChatServer(int serverPort) {
        try {
            this.socket = new DatagramSocket(serverPort);
            this.serverPort = serverPort;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建 UI
        setTitle("UDP 服务器");
        setLayout(new BorderLayout());

        // 消息区域
        messageArea = new JTextArea(15, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // 添加组件
        add(scrollPane, BorderLayout.CENTER);

        // 设置窗口基本属性
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);
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
                    messageArea.append("客户端: " + message + "\n");  // 显示客户端的消息

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
        UDPChatServer serverUI = new UDPChatServer(9876);
        serverUI.receiveMessage();  // 启动接收消息的线程
    }
}
```

