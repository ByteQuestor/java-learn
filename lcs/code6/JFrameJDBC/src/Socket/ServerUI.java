package Socket;

import javax.swing.*;

public class ServerUI {
    private static JTextArea chatArea;

    public ServerUI() {
        JFrame frame = new JFrame("聊天服务器");
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea));
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // 更新聊天区域显示
    public static void updateChatArea(String message) {
        chatArea.append(message + "\n");
    }
}
