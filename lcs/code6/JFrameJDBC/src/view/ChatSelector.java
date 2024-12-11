package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatSelector extends JFrame {
    private JButton socketChatButton, udpChatButton;

    public ChatSelector() {
        setTitle("选择聊天室类型");

        // 设置布局方式
        setLayout(new FlowLayout());

        // 创建按钮
        socketChatButton = new JButton("Socket 聊天室");
        udpChatButton = new JButton("UDP 聊天室");

        // 按钮事件
        socketChatButton.addActionListener(e -> startSocketChat());
        udpChatButton.addActionListener(e -> startUDPChat());

        // 添加组件
        add(socketChatButton);
        add(udpChatButton);

        // 设置窗口基本属性
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示窗口
        setVisible(true);
    }

    private void startSocketChat() {
        System.out.println("启动Socket聊天室");
        JOptionPane.showMessageDialog(this, "启动 Socket 聊天室...");
    }

    private void startUDPChat() {
        System.out.println("启动UDP聊天室");
        JOptionPane.showMessageDialog(this, "启动 UDP 聊天室...");
    }
}
