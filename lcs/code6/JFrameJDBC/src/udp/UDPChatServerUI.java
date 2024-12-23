package udp;

import javax.swing.*;
import java.awt.*;

public class UDPChatServerUI extends JFrame {
    private JTextArea messageArea;

    public UDPChatServerUI() {
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

    // 更新消息显示区域
    public void appendMessage(String message) {
        messageArea.append(message + "\n");
    }
}
