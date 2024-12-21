package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.UserController;

public class FormFactory extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userRoleComboBox;
    private JButton actionButton, resetButton;
    private JCheckBox toggleFormCheckBox;
    private JPanel formPanel;  // 用于存放表单的面板
    private GridBagConstraints gbc;

    public FormFactory() {
        // 默认初始化登录表单
        initLoginForm();
    }

    // 初始化公共表单部分
    public void initCommonForm(String formName) {
        // 设置窗口标题和大小
        setTitle(formName);
        setSize(350, 250);
        setLayout(new BorderLayout());

        // 标题标签
        JLabel titleLabel = new JLabel("图书管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 主面板，包含表单组件
        formPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距

        // 用户名标签和文本框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(usernameField, gbc);

        // 用户身份标签和下拉框
        JLabel userRoleLabel = new JLabel("用户身份:");
        String[] roles = { "admin", "reader" };
        userRoleComboBox = new JComboBox<>(roles);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userRoleLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(userRoleComboBox, gbc);

        // 密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(passwordField, gbc);
    }

    // 初始化登录表单
    public void initLoginForm() {
        initCommonForm("登录");

        // 按钮面板，包含登录和重置按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        actionButton = new JButton("登录");
        actionButton.addActionListener(new ActionButtonListener());
        buttonPanel.add(actionButton);

        resetButton = new JButton("重置");
        resetButton.addActionListener(new ResetButtonListener());
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // 添加面板到窗口
        add(formPanel, BorderLayout.CENTER);

        // 添加切换复选框（切换到注册）
        toggleFormCheckBox = new JCheckBox("切换到注册", false);
        toggleFormCheckBox.addActionListener(new ToggleFormListener());
        add(toggleFormCheckBox, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 初始化注册表单
    public void initRegisterForm() {
        initCommonForm("注册");

        // 按钮面板，包含注册和重置按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        actionButton = new JButton("注册");
        actionButton.addActionListener(new ActionButtonListener());
        buttonPanel.add(actionButton);

        resetButton = new JButton("重置");
        resetButton.addActionListener(new ResetButtonListener());
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 添加切换复选框（切换到登录）
        toggleFormCheckBox = new JCheckBox("切换到登录", true);
        toggleFormCheckBox.addActionListener(new ToggleFormListener());
        add(toggleFormCheckBox, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

 // 登录/注册按钮点击事件监听器
    private class ActionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            String userRole = (String) userRoleComboBox.getSelectedItem();

            // 获取按钮的文本
            String buttonText = actionButton.getText();

            UserController usercon = new UserController();

            if (buttonText.equals("登录")) {
                // 登录操作
                int option = usercon.checkLogin(username, password, userRole);
                if (option == 1) {
                    // 管理员界面
                    System.out.println("进入管理员面板");
                    new select(); // 管理员面板逻辑
                } else if (option == 2) {
                    // 读者界面
                    System.out.println("进入读者面板");
                } else {
                    System.out.println("未知错误");
                }
            } else if (buttonText.equals("注册")) {
                // 注册操作
                boolean isRegistered = usercon.addUser(null,username, password, userRole, null);
                if (isRegistered) {
                    System.out.println("注册成功");
                } else {
                    System.out.println("注册失败");
                }
            }
        }
    }


    // 重置按钮点击事件监听器
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            usernameField.setText("");
            passwordField.setText("");
            userRoleComboBox.setSelectedIndex(0);
        }
    }

    // 复选框切换表单监听器
    private class ToggleFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 切换表单时，移除当前面板并重新初始化相应的表单
            if (toggleFormCheckBox.isSelected()) {
                getContentPane().removeAll();  // 移除当前所有组件
                initRegisterForm();  // 初始化注册表单
            } else {
                getContentPane().removeAll();  // 移除当前所有组件
                initLoginForm();  // 初始化登录表单
            }
            revalidate();  // 重新布局
            repaint();  // 重新绘制
        }
    }
}
