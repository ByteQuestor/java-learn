package view;

import javax.swing.*;
import java.awt.*;

public class bookManager {
	private JFrame frame;
	private JPanel sidePanel; // 左侧导航栏
	private JPanel topPanel; // 顶部工具栏
	private JPanel contentPanel; // 中间内容区

	// 主函数作为程序入口
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new bookManager().createUI());
	}

	// 创建UI界面的方法
	public void createUI() {
		frame = new JFrame("图书管理系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
		frame.setLayout(new BorderLayout());

		// 主面板
		JPanel mainPanel = new JPanel(new BorderLayout());

		// 1. 左侧导航面板
		sidePanel = new JPanel();
		sidePanel.setBackground(Color.LIGHT_GRAY); // 设置背景色
		sidePanel.setPreferredSize(new Dimension(200, 600)); // 设置左侧面板的宽度
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // 垂直排列

		// 添加一个可伸缩的空白区域
		sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域

		// 在左侧导航栏中添加标签
		JLabel navigationLabel = new JLabel("这是导航区域", JLabel.CENTER);
		navigationLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 水平居中
		navigationLabel.setAlignmentY(Component.CENTER_ALIGNMENT); // 垂直居中
		sidePanel.add(navigationLabel); // 添加标签到左侧导航栏
		// 添加另一个可伸缩的空白区域，保持垂直居中
		sidePanel.add(Box.createVerticalGlue());  // 添加垂直方向的可伸缩空白区域

		
		// 2. 顶部工具栏
		topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY); // 设置背景色
		topPanel.setPreferredSize(new Dimension(1000, 50)); // 设置顶部面板的高度
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 水平排列

		// 添加简单的标签到顶部
		JLabel systemTitleLabel = new JLabel("图书管理系统");
		systemTitleLabel.setForeground(Color.WHITE);
		topPanel.add(systemTitleLabel);

		// 3. 中间内容面板（使用CardLayout）
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE); // 设置内容区的背景色
		contentPanel.setLayout(new BorderLayout()); // 用BorderLayout管理内容区

		// 添加简单的标签到内容区
		JLabel contentLabel = new JLabel("这是内容区域", JLabel.CENTER);
		contentPanel.add(contentLabel, BorderLayout.CENTER);

		// 将左侧面板、顶部面板和中间面板加入主面板
		mainPanel.add(sidePanel, BorderLayout.WEST);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// 将主面板添加到框架中
		frame.add(mainPanel);
		frame.setVisible(true);
	}
}
