package view;

//public class Login {

//}

import java.awt.Component; 
import java.awt.Event; 
import java.awt.Font; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 

import javax.security.auth.kerberos.KeyTab; 
import javax.swing.JButton; 
import javax.swing.JDialog; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JPasswordField; 
import javax.swing.JTextField; 

/**
 * 类：登陆界面设置
 * @author Abe
 * 父类JDialog 接口ActionListenner
 */
@SuppressWarnings("serial")
public class Login extends JDialog implements ActionListener {
    private JButton loginButton, cancelButton;
    private JLabel uidLabel, pwdLabel;
    private JTextField uidField;
    private JPasswordField pwdField; 

    public Login() {
        this.setSize(320, 240);
        this.setTitle("图书馆管理系统");
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        // 窗口关闭事件监听
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "亲~真的要离开我吗？~") == 0) {
                    System.exit(0);
                }
            }
        });

        initComponents(); 
    }

    /**
     * 方法：组件添加
     */
    private void initComponents() {
        loginButton = new JButton("登陆");
        loginButton.setBounds(70, 160, 80, 30);
        this.add(loginButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(170, 160, 80, 30);
        this.add(cancelButton);

        uidLabel = new JLabel("用户名：");
        uidLabel.setBounds(50, 50, 60, 30);
        this.add(uidLabel);

        pwdLabel = new JLabel("密 码：");
        pwdLabel.setBounds(50, 100, 60, 30);
        this.add(pwdLabel);

        uidField = new JTextField();
        uidField.setBounds(120, 50, 120, 30);
        this.add(uidField);

        pwdField = new JPasswordField();
        pwdField.setBounds(120, 100, 120, 30);
        this.add(pwdField);

        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        for (Component c : this.getContentPane().getComponents()) {
            c.setFont(font);
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(this);
            }
        }

        // 键盘监听，回车切换焦点和点击登录
        uidField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    pwdField.requestFocus();
                }
            }
        });
        pwdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    loginButton.doClick();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 在此添加按钮点击事件的处理逻辑
        if (e.getSource() == loginButton) {
            // 登录按钮点击事件处理
        } else if (e.getSource() == cancelButton) {
            // 取消按钮点击事件处理
        }
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}