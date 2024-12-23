package view;

import javax.swing.*;

import lcs.LcsBox;
import util.DBCon;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    
    public LoginFrame() {
        setTitle("登录窗口");

        // 设置布局方式为垂直方向的BoxLayout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("登录");
        registerButton = new JButton("注册");

        usernameField.setPreferredSize(new Dimension(200, 30)); // 设置文本框的大小
        passwordField.setPreferredSize(new Dimension(200, 30)); // 设置密码框的大小
        loginButton.setPreferredSize(new Dimension(100, 30)); // 设置按钮大小
        registerButton.setPreferredSize(new Dimension(100, 30)); // 设置按钮大小

        // 添加组件
        add(new JLabel("用户名："));
        add(usernameField);
        add(new JLabel("密码："));
        add(passwordField);
        add(loginButton);
        add(registerButton);

        // 按钮的事件监听
        loginButton.addActionListener(e -> loginUser());
        registerButton.addActionListener(e -> openRegisterFrame());

        // 设置窗口基本属性
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示窗口
        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBCon.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, username);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "用户名和密码正确！");
                    openChatSelector(); //打开选择器
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "登录失败！");
        }
    }

    private void openRegisterFrame() {
        // 创建并显示注册窗口
        new RegisterFrame();
    }

    public static void main(String[] args) {
    	//1,样式1
        //new LoginFrame();
    	
    	//2,样式2
    	LcsBox win = new LcsBox();
    	win.setBounds(100,100,310,260);
    	win.setTitle("登录框");
    }

    private void openChatSelector() {
        // 创建并显示选择器窗口
        new ChatSelector();
    }
}

