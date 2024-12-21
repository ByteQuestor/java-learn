|-- Borrow.java
|       |-- Choose.java
|       |-- LibraryLoginFrame.java
|       |-- Return.java
|       |-- bookManager.java
|       |-- buy.java
|       |-- query.java
|       |-- rank.java
|       |-- refound.java
|       |-- replenish.java
|       `-- select.java|-- Borrow.java
|       |-- Choose.java
|       |-- LibraryLoginFrame.java
|       |-- Return.java
|       |-- bookManager.java
|       |-- buy.java
|       |-- query.java
|       |-- rank.java
|       |-- refound.java
|       |-- replenish.java
|       `-- select.java# 图书管理系统说明书

项目地址：`https://github.com/ByteQuestor/java-learn/tree/main/wzy/bookManager`

# `v1.0`

## 项目结构

```tex

src/
├── main
│   └── Main.java				# 程序入口
├── model
│   ├── DatabaseManager.java	# 数据库连接类
└── view
      |-- Borrow.java                  #借书窗口
|     |-- Choose.java                     #读者选择窗口
|     |-- LibraryLoginFrame.java      #登录窗口
|     |-- Return.java                   #还书窗口  
|     |-- bookManager.java                  #旧的UI
|     |-- buy.java                               #买书窗口
|     |-- query.java                             #查询窗口
|     |-- rank.java                              #排行榜
|     |-- refound.java                           #退货窗口
|     |-- replenish.java                         #进货窗口
|     |-- select.java                    #管理员选择窗口
src/
└── bookmanage.sql				# 数据库转储文件，内置数据库-数据表创建，直接运行转储文件即可
```

## 一、数据库设计

### 数据库名称：`bookmanage`

```sql
-- 创建数据库
CREATE DATABASE bookmanage;

-- 使用数据库
USE bookmanage;
```

### 表：`book`

> 书籍表，保存图书信息

| 字段名             | 数据类型         | 描述             |
| ------------------ | ---------------- | ---------------- |
| `bno`              | `INT`            | 书籍编号（主键） |
| `bname`            | `VARCHAR(255)`   | 书籍名称         |
| `bstore`           | `INT`            | 书籍库存         |
| `bsale`            | `INT`            | 书籍销量         |
| `author`           | `VARCHAR(255)`   | 作者             |
| `publisher`        | `VARCHAR(255)`   | 出版社           |
| `category`         | `VARCHAR(255)`   | 分类             |
| `price`            | `DECIMAL(10, 2)` | 书籍价格         |
| `publication_year` | `YEAR`           | 出版年份         |

**表结构说明：**

- **`bno`**：书籍编号，主键，自增长。
- **`bname`**：书籍名称，可以作为查询条件。
- **`bstore`**：书籍库存，表示书籍的数量。
- **`bsale`**：书籍销量，用于记录书籍销售的数量。
- **`author`**：作者信息，字符串类型。
- **`publisher`**：出版社信息，字符串类型。
- **`category`**：书籍分类，字符串类型。
- **`price`**：书籍价格，数字类型，保留两位小数。
- **`publication_year`**：出版年份，存储年份信息。

### 表：`users`

> 用户表，保存用户信息

```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,  -- 用户ID，自动增长
    username VARCHAR(50) NOT NULL,           -- 用户名，唯一
    password VARCHAR(255) NOT NULL,          -- 密码，建议加密存储
    user_role ENUM('admin', 'reader') NOT NULL,  -- 用户角色，区分管理员和读者
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 记录创建时间
    last_login TIMESTAMP NULL DEFAULT NULL     -- 最后登录时间
);
```

**表结构说明：**

+ **`user_id`**: 主键，唯一标识每个用户。
+ **`username`**: 用户名，`VARCHAR(50)`，不允许为空，且需要唯一。
+ **`password`**: 用户密码，存储加密后的密码（通常使用哈希算法加密，如`bcrypt`或`SHA-256`）。长度设为`VARCHAR(255)`以存储哈希值。
+ **`user_role`**: 用户角色字段，使用`ENUM`类型来限定角色，可能的值为`admin`或`reader`，用于区分管理员和普通读者
+ **`create_time`**: 用户创建时间，`TIMESTAMP`类型，默认当前时间。
+ **`last_login`**: 最后登录时间，`TIMESTAMP`类型，默认为`NULL`，可以记录用户的最后一次登录。

## 二、程序代码

### 主入口

```java
package main;

import view.LibraryLoginFrame;

public class Main {
    public static void main(String[] args) {
        // 创建并显示登录窗口
        new LibraryLoginFrame();
    }
}
```

### 数据库链接

```java
package model;

import java.sql.*;

public class DatabaseManager {

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/bookmanage";
    private static final String USER = "root";       // 请替换为你的数据库用户名
    private static final String PASSWORD = "000000";  // 请替换为你的数据库密码

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("数据库连接失败！");
        }
    }

    // 关闭连接
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
		} catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### 主要UI界面

#### 公共部分

登录`LibraryLoginFrame.java`

```java
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
```

管理员选择界面`select.java`

```java
package view;
import javax.swing.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class select extends JFrame implements ActionListener{
	ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
	//JLabel label1=new JLabel(icon1);
    //label1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
	//JLabel Lab=new JLabel(" 图书管理系统信息查询：");
	JLabel Lab=new JLabel(icon1);
    JButton Btn1=new JButton(" 图书信息查询");
    JButton Btn2=new JButton("  购买书籍");
    JButton Btn3=new JButton("  书籍排行榜");
    JButton Btn4=new JButton("  书籍退货");  
    JButton Btn5=new JButton("  书籍进货");
    JButton Btn6=new JButton("退出");
    public select(){
    	setTitle("信息查询");
		setVisible(true);
		setBounds(500,300,250,200);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(select.EXIT_ON_CLOSE);
		Container e=getContentPane();
		 e.add(Lab);  e.add(Btn1);
		 e.add(Btn2); e.add(Btn3);
	     e.add(Btn4); e.add(Btn5);
		 e.add(Btn6);
		Btn1.addActionListener(this); Btn2.addActionListener(this);
		Btn3.addActionListener(this); 
		Btn4.addActionListener(this);
		 Btn5.addActionListener(this);
		 Btn6.addActionListener(this);}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Btn1){  new query();  }
		if(e.getSource()==Btn2){  new buy(); }
		if(e.getSource()==Btn3){  new rank();}
		if(e.getSource()==Btn4){  new refound();  }
		if(e.getSource()==Btn5){   new replenish();}
	    if(e.getSource()==Btn6){  setVisible(false);  dispose();}
		}
	public static void main(String[] args) {
		new select();}
}
```

图书信息查询`query.java`

```java
package view;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class query extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	static JTextField Text;
	public query(){
		super("图书查询");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(100,15,150,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("书名查询");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("全部书籍信息");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出查询");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    add(this.Text);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}

	public void btnShow_ActionPerformed(ActionEvent ae){
		try{//String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//String connectDB="jdbc:sqlserver://localhost:3306;DatabaseName=BooksManagement";
			//String connectDB="jdbc:mysql://localhost:3306/dingbao?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//String user="root";   String password="1234";
			//Class.forName(JDriver);
			//Connection con=DriverManager.getConnection(connectDB,user,password);
			//Statement stmt=con.createStatement();
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="000000";
//			Class.forName("com.mysql.jdbc.Driver");
			Connection con= DatabaseManager.getConnection();//封装数据库连接
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1) 
				sql="select* from book";
			
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][6];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				info[count][3] = rs.getString("book.bsale");
				info[count][4] = rs.getString("book.price");
				info[count][5] = rs.getString("book.publisher");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量","书籍加个","出版社"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new query();
	}
}
```

购买书籍`buy.java`

```java
package view;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class buy extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	private JLabel Lab2;
	static JTextField Text;
	static JTextField Text2;
	public buy(){
		super("图书购买");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(65,15,150,30);
		this.Lab2=new JLabel("购买数量");
		this.Lab2.setBounds(215,15,100,30);
		this.Text2=new JTextField(5);
		this.Text2.setBounds(270,15,70,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("书名查询");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("购买此书");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出购买");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    
		add(this.Lab2);  
		add(this.Text);
		add(this.Text2);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{//String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//String connectDB="jdbc:sqlserver://localhost:3306;DatabaseName=BooksManagement";
			//String connectDB="jdbc:mysql://localhost:3306/dingbao?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//String user="root";   String password="1234";
			//Class.forName(JDriver);
			//Connection con=DriverManager.getConnection(connectDB,user,password);
			//Statement stmt=con.createStatement();
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
			//Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";		
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1)
			{
				String[] sqls = new String[4];
				int value = Integer.parseInt(Text2.getText());
				//System.out.println(value);
				//int value2;
			    sqls[0] = "SET SQL_SAFE_UPDATES = 0";
			    sqls[1] = "set @value2="+value;
			    sqls[2] = "update book set bstore=bstore-@value2 where book.bname='" + Text.getText()+"'";
			    sqls[3] = "update book set bsale=bsale+@value2 where book.bname='" + Text.getText()+"'";
			    Statement sm = null;
			    sm = con.createStatement();
	            for (int i = 0; i < sqls.length; i++) {
	                sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new buy();
	}
}
```

书籍排行榜`rank.java`

```java
package view;
import javax.swing.*;
//import java.awt.*;


import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class rank extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	static JTextField Text;
	public rank(){
		super("图书排行榜");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书排行榜");
		ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab.setBounds(150,15,100,30);
		this.Text=new JTextField(15);
		this.Text.setBounds(100,15,150,30);
		//this.scpDemo = new JScrollPane();
		this.Lab1=new JLabel(icon1);
        //设置label的位置、大小，label大小为图片的大小
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("销量排行榜");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("库存排行榜");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出排行榜");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);   // add(this.Text);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}

	public void btnShow_ActionPerformed(ActionEvent ae){
		try{//String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//String connectDB="jdbc:sqlserver://localhost:3306;DatabaseName=BooksManagement";
			//String connectDB="jdbc:mysql://localhost:3306/dingbao?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//String user="root";   String password="1234";
			//Class.forName(JDriver);
			//Connection con=DriverManager.getConnection(connectDB,user,password);
			//Statement stmt=con.createStatement();
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				//sql = "select* from book where book.bname='" + Text.getText()+"'";
				sql="SELECT * from book order by -bsale";
			if(ae.getSource()==btn1) 
				sql="select* from book order by -bstore";
			
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new rank();
	}
}
```

书籍退货`refound.java`

```java
package view;


import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class refound extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	private JLabel Lab2;
	static JTextField Text;
	static JTextField Text2;
	public refound(){
		super("图书退货");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(65,15,150,30);
		this.Lab2=new JLabel("退货数量");
		this.Lab2.setBounds(215,15,100,30);
		this.Text2=new JTextField(5);
		this.Text2.setBounds(270,15,70,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("书名查询");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("退货此书");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出退货");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    
		add(this.Lab2);  
		add(this.Text);
		add(this.Text2);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";		
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1)
			{
				String[] sqls = new String[4];
				int value = Integer.parseInt(Text2.getText());
			    sqls[0] = "SET SQL_SAFE_UPDATES = 0";
			    sqls[1] = "set @value2="+value;
			    sqls[2] = "update book set bstore=bstore+@value2 where book.bname='" + Text.getText()+"'";
			    sqls[3] = "update book set bsale=bsale-@value2 where book.bname='" + Text.getText()+"'";
			    Statement sm = null;
			    sm = con.createStatement();
	            for (int i = 0; i < sqls.length; i++) {
	                sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new refound();
	}
}
```

书籍进货`replenish.java`

```java
package view;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class replenish extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1,btn2;
	private JLabel Lab;
	private JLabel Lab1;
	private JLabel Lab2;
	static JTextField Text;
	static JTextField Text2;
	public replenish(){
		super("图书进货");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		ImageIcon icon1=new ImageIcon("C:/Users/谭永苇/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(65,15,150,30);
		this.Lab2=new JLabel("进货数量");
		this.Lab2.setBounds(215,15,100,30);
		this.Text2=new JTextField(5);
		this.Text2.setBounds(270,15,70,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("查询");
		this.btnShow.setBounds(350,15,60,30);
		this.btn1 = new JButton("旧书补货");
		this.btn1.setBounds(420,15,100,30);
		this.btn2 = new JButton("新书进货");
		this.btn2.setBounds(530,15,100,30);
		this.btn=new JButton("退出购买");
		this.btn.setBounds(530,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    
		add(this.Lab2);  
		add(this.Text);
		add(this.Text2);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1); add(this.btn2);
		add(this.Lab1);
		this.setVisible(true);
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{//String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//String connectDB="jdbc:sqlserver://localhost:3306;DatabaseName=BooksManagement";
			//String connectDB="jdbc:mysql://localhost:3306/dingbao?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//String user="root";   String password="1234";
			//Class.forName(JDriver);
			//Connection con=DriverManager.getConnection(connectDB,user,password);
			//Statement stmt=con.createStatement();
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";		
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1)
			{
				String[] sqls = new String[3];
				int value = Integer.parseInt(Text2.getText());
				//System.out.println(value);
				//int value2;
			    sqls[0] = "SET SQL_SAFE_UPDATES = 0";
			    sqls[1] = "set @value2="+value;
			    sqls[2] = "update book set bstore=bstore+@value2 where book.bname='" + Text.getText()+"'";
			    Statement sm = null;
			    sm = con.createStatement();
	            for (int i = 0; i < sqls.length; i++) {
	                sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			if(ae.getSource()==btn2)
			{
				String[] sqls1 = new String[2];
				int value1 = Integer.parseInt(Text2.getText());
				//System.out.println(value);
				//int value2;
			    sqls1[0] = "set @value2="+value1;
			    sqls1[1] = "INSERT INTO book(bname, bstore, bsale) VALUES('" + Text.getText()+"', @value2, 0)";
			    Statement sm1 = null;
			    sm1 = con.createStatement();
	            for (int i = 0; i < sqls1.length; i++) {
	                sm1.addBatch(sqls1[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm1.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new replenish();
	}
}
读者选择页面'Choose.java'
package view;

import javax.swing.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class Choose extends JFrame implements ActionListener{
	ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
	JLabel Lab=new JLabel(icon1);
    JButton Btn1=new JButton(" 借阅书籍");
    JButton Btn2=new JButton("  归还书籍");
    JButton Btn3=new JButton("退出");
    public Choose(){
    	setTitle("选择操作");
		setVisible(true);
		setBounds(500,300,250,200);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(Choose.EXIT_ON_CLOSE);
		Container e=getContentPane();
		 e.add(Lab);  e.add(Btn1);
		 e.add(Btn2); e.add(Btn3);
		Btn1.addActionListener(this); Btn2.addActionListener(this);
		Btn3.addActionListener(this);}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Btn1){  new Borrow();  }
		if(e.getSource()==Btn2){  new Return(); }
		if(e.getSource()==Btn3){  setVisible(false);  dispose();}
		}
	public static void main(String[] args) {
		new Choose();}}

/*public class select {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/
借阅书籍'Borrow.java'
package view;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class Borrow extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	private JLabel Lab2;
	static JTextField Text;
	static JTextField Text2;
	public Borrow(){
		super("图书借阅");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(65,15,150,30);
		this.Lab2=new JLabel("借阅数量");
		this.Lab2.setBounds(215,15,100,30);
		this.Text2=new JTextField(5);
		this.Text2.setBounds(270,15,70,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("书名查询");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("借阅此书");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出借阅");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    
		add(this.Lab2);  
		add(this.Text);
		add(this.Text2);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{//String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			//String connectDB="jdbc:sqlserver://localhost:3306;DatabaseName=BooksManagement";
			//String connectDB="jdbc:mysql://localhost:3306/dingbao?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//String user="root";   String password="1234";
			//Class.forName(JDriver);
			//Connection con=DriverManager.getConnection(connectDB,user,password);
			//Statement stmt=con.createStatement();
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
			//Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";		
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1)
			{
				String[] sqls = new String[4];
				int value = Integer.parseInt(Text2.getText());
				//System.out.println(value);
				//int value2;
			    sqls[0] = "SET SQL_SAFE_UPDATES = 0";
			    sqls[1] = "set @value2="+value;
			    sqls[2] = "update book set bstore=bstore-@value2 where book.bname='" + Text.getText()+"'";
			    sqls[3] = "update book set bsale=bsale+@value2 where book.bname='" + Text.getText()+"'";
			    Statement sm = null;
			    sm = con.createStatement();
	            for (int i = 0; i < sqls.length; i++) {
	                sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				//info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new Borrow();
	}
}

/*public class buy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/
归还书籍'Return.java'
package view;
import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import model.DatabaseManager;

//import swing.Book;
//import swing.Select;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.*;

public class Return extends JFrame{
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;
	private JButton btn,btn1;
	private JLabel Lab;
	private JLabel Lab1;
	private JLabel Lab2;
	static JTextField Text;
	static JTextField Text2;
	public Return(){
		super("图书归还");
		this.setSize(650,600);
		this.setLayout(null);
		this.setLocation(250,100);
		this.Lab=new JLabel("图书名称");
		this.Lab.setBounds(10,15,100,30);
		ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
		icon1.setImage(icon1.getImage().getScaledInstance(632,650,java.awt.Image.SCALE_DEFAULT));
		this.Lab1=new JLabel(icon1);
		Lab1.setBounds(0,0,icon1.getIconWidth(),icon1.getIconHeight());
		this.Text=new JTextField(15);
		this.Text.setBounds(65,15,150,30);
		this.Lab2=new JLabel("归还数量");
		this.Lab2.setBounds(215,15,100,30);
		this.Text2=new JTextField(5);
		this.Text2.setBounds(270,15,70,30);
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(10,60,600,400);
		scpDemo.setOpaque(false);
		scpDemo.getViewport().setOpaque(false); 
		this.btnShow = new JButton("书名查询");
		this.btnShow.setBounds(350,15,100,30);
		this.btn1 = new JButton("归还此书");
		this.btn1.setBounds(480,15,130,30);
		this.btn=new JButton("退出归还");
		this.btn.setBounds(480,500,100,30);
		this.btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		this.btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed1(ae);}});
		this.btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);}});
		add(this.Lab);    
		add(this.Lab2);  
		add(this.Text);
		add(this.Text2);
		add(this.scpDemo); add(this.btnShow);
		add(this.btn);   add(this.btn1);
		add(this.Lab1);
		this.setVisible(true);
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{
//			String JDriver="com.mysql.jdbc.Driver";
//			String connectDB="jdbc:mysql://localhost:3306/bookmanage?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//			String user="root";
//			String password="1234";
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con=DriverManager.getConnection(connectDB, user, password);
			Connection con=DatabaseManager.getConnection();//封装
			Statement stmt=con.createStatement();
	
	
			String sql="SELECT * from book";		
			ResultSet rs=stmt.executeQuery(sql);
	
			if(ae.getSource()==btnShow)
				sql = "select* from book where book.bname='" + Text.getText()+"'";
			if(ae.getSource()==btn1)
			{
				String[] sqls = new String[4];
				int value = Integer.parseInt(Text2.getText());
			    sqls[0] = "SET SQL_SAFE_UPDATES = 0";
			    sqls[1] = "set @value2="+value;
			    sqls[2] = "update book set bstore=bstore+@value2 where book.bname='" + Text.getText()+"'";
			    sqls[3] = "update book set bsale=bsale-@value2 where book.bname='" + Text.getText()+"'";
			    Statement sm = null;
			    sm = con.createStatement();
	            for (int i = 0; i < sqls.length; i++) {
	                sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
	            }
	            // 一次执行多条SQL语句
	            sm.executeBatch();
	            //System.out.println(value);
	            sql = "select* from book where book.bname='" + Text.getText()+"'";
			}
			int count = 0;
			while(rs.next()){count++;}
			rs = stmt.executeQuery(sql);
			Object[][] info = new Object[count][4];    
			count = 0;
   
			while(rs.next()){
				info[count][0] =  rs.getString("book.bno");
				info[count][1] = rs.getString("book.bname");
				info[count][2] =  rs.getString("book.bstore");
				//info[count][3] = rs.getString("book.bsale");
				//info[count][4] = rs.getString("出版社");
				//info[count][5] = rs.getString("所在书库");
				count++;}
			
			String[] title = {"书籍编号","书籍名称","书籍库存", "书籍销量"};
			this.tabDemo = new JTable(info,title);
			this.jth = this.tabDemo.getTableHeader();
			this.scpDemo.getViewport().add(tabDemo); 
		}
//		catch(ClassNotFoundException e){  System.exit(0);}
		catch(SQLException sqle){
			JOptionPane.showMessageDialog(null,"数据操作错误","错误",JOptionPane.ERROR_MESSAGE);
			}
	}




	public void btnShow_ActionPerformed1(ActionEvent ae){
		setVisible(false);   
		dispose();    
		//new Select();
		}



	public static void main(String[] args){
		new Return();
	}
}

/*public class refound {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/



```

## 三、现有阶段的不足

+ 【图书信息查询】按名字查询未实现
+ 【购买书籍】仅仅实现了面板，书名查询未实现
+ 【书籍排行榜】仅仅未实现销量排行榜
+ 【书籍退货】未实现名字查询
+ 【书籍进货】未实现名字查询
+ 【登录】登录时间没有记录

## 四、计划补充

+ 注册功能
+ 名字查询
+ 在登陆后插入当前时间

