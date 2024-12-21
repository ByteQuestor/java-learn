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
		ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
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

//package bookmanage;



/*public class replenish {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/
