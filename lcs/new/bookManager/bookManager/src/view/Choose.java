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
