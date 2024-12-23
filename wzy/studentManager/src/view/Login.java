package view;

import javax.swing.*;

import controller.BindView;

import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    private JButton studentLoginButton, teacherLoginButton, cancelButton;
    private JTextField uidField;
    private JPasswordField pwdField;

    public Login() {
        // 设置窗口基本属性
        this.setSize(300, 200);
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
        studentLoginButton.setBounds(40, 120, 100, 30);
        studentLoginButton.addActionListener(this);
        this.add(studentLoginButton);

        // 老师登录按钮
        teacherLoginButton = new JButton("老师登录");
        teacherLoginButton.setBounds(160, 120, 100, 30);
        teacherLoginButton.addActionListener(this);
        this.add(teacherLoginButton);

        // 取消按钮
        cancelButton = new JButton("取消");
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
        } else if (e.getSource() == cancelButton) {
            // 清空输入框
            uidField.setText("");
            pwdField.setText("");
        }
    }

    private void login(int role) {
    	if(role == 0) {
    		//学生登录
            JOptionPane.showMessageDialog(this,"学生视图未开发");
    	}else if(role == 1){
    		//老师登录
    		//封装视图绑定
            BindView teacherView = new BindView();
            teacherView.bind();
    	}
        String username = uidField.getText();
        String password = new String(pwdField.getPassword());

    }

//    public static void main(String[] args) {
//    	System.out.print("aaaa");
//        new Login().setVisible(true);
//    }
}
