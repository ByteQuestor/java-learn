package view;

import javax.swing.*;

import model.DatabaseManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
public class LibraryLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userRoleComboBox;
    private JButton loginButton;
    private JButton resetButton;

    public LibraryLoginFrame() {
        // 设置窗口标题为"登录"，实现导航栏只有"登录"两个字
        setTitle("登录");
        // 设置窗口大小
        setSize(350, 250);
        // 使用BorderLayout布局管理器，便于整体布局
        setLayout(new BorderLayout());
        

        // 创建用于显示"图书管理系统"文字的标签，并设置样式及位置
        JLabel titleLabel = new JLabel("图书管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 创建中间面板，用于放置登录相关的组件，使用GridBagLayout来灵活布局各组件
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // 设置组件间距

        // 创建用户名标签和文本框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(usernameField, gbc);

        // 创建用户身份标签和下拉框
        JLabel userRoleLabel = new JLabel("用户身份:");
        String[] roles = {"admin", "reader"};  // 可根据实际需求扩展身份种类
        userRoleComboBox = new JComboBox<>(roles);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userRoleLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(userRoleComboBox, gbc);

        // 创建密码标签和密码框
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(passwordField, gbc);

        // 创建按钮面板，用于放置登录和重置按钮，使用FlowLayout使其占一行并居中显示
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 创建登录按钮并添加点击事件监听器
        loginButton = new JButton("登录");
        loginButton.addActionListener(new LoginButtonListener());
        buttonPanel.add(loginButton);

        // 创建重置按钮并添加点击事件监听器
        resetButton = new JButton("重置");
        resetButton.addActionListener(new ResetButtonListener());
        buttonPanel.add(resetButton);

        // 将按钮面板添加到登录面板中合适的位置
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonPanel, gbc);

        // 将包含登录组件的面板添加到窗口中间区域
        add(loginPanel, BorderLayout.CENTER);

        // 设置窗口可见
        setVisible(true);
        // 设置默认关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 登录按钮点击事件监听器内部类
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            String userRole = (String) userRoleComboBox.getSelectedItem();

            try {
                // 建立数据库连接（根据实际情况修改URL、用户名、密码）
               Connection connection = DatabaseManager.getConnection();
                
                // 准备SQL查询语句，防止SQL注入
                String sql = "SELECT * FROM users WHERE username =? AND password =? AND user_role =?";
                //Connection connection;
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, userRole);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "登录成功！");
                    // 添加跳转到对应功能页面的逻辑，比如根据不同身份进入不同的管理或阅读界面
//                    // 获取选择的用户身份
                    String selectedRole = (String) userRoleComboBox.getSelectedItem();
//					// 输出选择的用户身份
//					System.out.println("选择的用户身份是: " + selectedRole);
                    if(selectedRole.equals("admin")) {
                    	//进入管理员界面
                    	new select();
                    }
                    else {
                    	//进入读者界面
                    	JOptionPane.showMessageDialog(null, "读者界面还没开发");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "用户名、密码或用户身份错误，请重新输入！");
                }

                // 关闭资源
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "数据库连接或查询出错，请检查配置！");
            }
        }
    }

    // 重置按钮点击事件监听器内部类
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            usernameField.setText("");
            passwordField.setText("");
            userRoleComboBox.setSelectedIndex(0);
        }
    }

    
}