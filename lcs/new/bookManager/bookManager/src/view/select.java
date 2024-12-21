package view;
import javax.swing.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class select extends JFrame implements ActionListener{
	ImageIcon icon1=new ImageIcon("C:/Users/Desktop/book.jpg" );
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
    	setTitle("管理员操作页面");
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
		new select();}}

/*public class select {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}*/