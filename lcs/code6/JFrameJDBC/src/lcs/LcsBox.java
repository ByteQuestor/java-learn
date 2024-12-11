package lcs;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LcsBox extends JFrame{
	Box boxH;
	Box boxVOne,boxVTwo;
	public LcsBox() {
		setLayout(new java.awt.FlowLayout());
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		boxH = Box.createHorizontalBox();
		boxVOne = Box.createVerticalBox();
		boxVTwo = Box.createVerticalBox();
		boxVOne.add(new JLabel("姓名:"));
		boxVTwo.add(new JLabel("职业:"));
		boxVOne.add(new JTextField(10));
		boxVTwo.add(new JTextField(10));
		boxH.add(boxVOne);
		boxH.add(Box.createHorizontalStrut(10));
		boxH.add(boxVTwo);
		add(boxH);
	}
}