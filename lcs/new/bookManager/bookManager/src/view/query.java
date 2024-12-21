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
		ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
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

