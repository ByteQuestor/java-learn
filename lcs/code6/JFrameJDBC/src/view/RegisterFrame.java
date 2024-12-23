package view;

import javax.swing.*;
import util.DBCon;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterFrame extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton registerButton, resetButton;

	public RegisterFrame() {
		setTitle("注册窗口");

		// 设置布局方式为垂直方向的BoxLayout
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		registerButton = new JButton("注册");
		resetButton = new JButton("重置");

		usernameField.setPreferredSize(new Dimension(200, 30)); // 设置文本框的大小
		passwordField.setPreferredSize(new Dimension(200, 30)); // 设置密码框的大小
		registerButton.setPreferredSize(new Dimension(100, 30)); // 设置按钮大小
		resetButton.setPreferredSize(new Dimension(100, 30)); // 设置按钮大小

		// 添加组件
		add(new JLabel("用户名："));
		add(usernameField);
		add(new JLabel("密码："));
		add(passwordField);
		add(registerButton);
		add(resetButton);

		// 按钮的事件监听
		registerButton.addActionListener(e -> registerUser());
		resetButton.addActionListener(e -> resetFields());

		// 设置窗口基本属性
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 居中显示窗口
		setVisible(true);
	}

	private void registerUser() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());

		try (Connection conn = DBCon.getConnection()) {
			String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
			try (PreparedStatement pst = conn.prepareStatement(sql)) {
				pst.setString(1, username);
				pst.setString(2, password);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(this, "注册成功！");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "注册失败！");
		}
	}

	private void resetFields() {
		usernameField.setText("");
		passwordField.setText("");
	}

	public static void main(String[] args) {
		new RegisterFrame();
	}
}