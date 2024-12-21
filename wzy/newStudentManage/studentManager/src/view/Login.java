package view;

import javax.swing.*;

import controller.BindView;
import controller.StudentController;
import controller.UserController;

import java.awt.event.*;
import java.util.List;

public class Login extends JFrame implements ActionListener {
	private JButton studentLoginButton, teacherLoginButton, cancelButton, adminLoginButton;
	private JTextField uidField;
	private JPasswordField pwdField;
	UserController userController = new UserController(null);// 验证登录是否成功

	public Login() {
		// 设置窗口基本属性
		this.setSize(350, 250);
		this.setTitle("登录系统");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		// 初始化组件
		initComponents();
	}

	private void initComponents() {
		// 学生登录按钮
		studentLoginButton = new JButton("学生登录");
		studentLoginButton.setBounds(10, 120, 100, 30);
		studentLoginButton.addActionListener(this);
		this.add(studentLoginButton);

		// 老师登录按钮
		teacherLoginButton = new JButton("老师登录");
		teacherLoginButton.setBounds(100, 120, 100, 30);
		teacherLoginButton.addActionListener(this);
		this.add(teacherLoginButton);

		// 管理员登录按钮
		adminLoginButton = new JButton("管理登录");
		adminLoginButton.setBounds(200, 120, 100, 30);
		adminLoginButton.addActionListener(this);
		this.add(adminLoginButton);

		// 取消按钮
		cancelButton = new JButton("重置");
		cancelButton.setBounds(100, 160, 100, 30);
		cancelButton.addActionListener(this);
		this.add(cancelButton);

		// 用户名输入框
		uidField = new JTextField();
		uidField.setBounds(100, 40, 120, 30);
		this.add(uidField);

		// 密码输入框
		pwdField = new JPasswordField();
		pwdField.setBounds(100, 80, 120, 30);
		this.add(pwdField);

		// 标签
		JLabel uidLabel = new JLabel("用户名：");
		uidLabel.setBounds(30, 40, 60, 30);
		this.add(uidLabel);

		JLabel pwdLabel = new JLabel("密码：");
		pwdLabel.setBounds(30, 80, 60, 30);
		this.add(pwdLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == studentLoginButton) {
			login(0);
		} else if (e.getSource() == teacherLoginButton) {
			login(1);
		} else if (e.getSource() == adminLoginButton) {
			login(2);
		} else if (e.getSource() == cancelButton) {
			// 清空输入框
			uidField.setText("");
			pwdField.setText("");
		}
	}

	private void login(int role) {
		String username = uidField.getText();
		String password = new String(pwdField.getPassword());
		String sql_role = String.valueOf(role);

		BindView View = new BindView();
		if (role == 0 && userController.studentLogin(username, password)) {
			// 学生登录
			//JOptionPane.showMessageDialog(this, "学生视图未开发");
			// 封装视图绑定
			//BindView teacherView = new BindView();
			View.bind(0,username,password);
		} else if (role == 1 && userController.login(username, password, sql_role)) {
			// 老师登录
			// 封装视图绑定
			//BindView teacherView = new BindView();
			//teacherView.bind(1);
			View.bind(1,"I am a teacher","000000");
		} else if (role == 2 && userController.login(username, password, sql_role)) {
			// 管理员登录
			// 封装视图绑定
			// JOptionPane.showMessageDialog(this, "管理员视图未开发");
			// 封装视图绑定
			//BindView teacherView = new BindView();
			//teacherView.bind(2);
			View.bind(2,"I am a admin","000000");
		} else {
			JOptionPane.showMessageDialog(this, "密码错误！");
		}

	}

//    public static void main(String[] args) {
//    	System.out.print("aaaa");
//        new Login().setVisible(true);
//    }
}
