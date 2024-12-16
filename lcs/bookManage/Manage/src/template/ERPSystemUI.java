package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ERPSystemUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel contentPanel;

    // main函数作为程序入口
    public static void main(String[] args) {
        // 使用SwingUtilities.invokeLater确保UI组件在正确的线程上创建
        SwingUtilities.invokeLater(() -> new ERPSystemUI().createUI());
    }

    // 创建UI界面的方法
    public void createUI() {
        frame = new JFrame("ERP系统UI模仿");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // 主面板
        mainPanel = new JPanel(new BorderLayout());

        // 左侧导航面板
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // 一级菜单按钮（图书管理、订单管理等）
        JButton bookManagementButton = new JButton("图书管理");
        JButton orderManagementButton = new JButton("订单管理");
        JButton financeManagementButton = new JButton("财务管理");

        // 二级菜单：订单管理下的子菜单按钮（使用JButton模拟下拉菜单）
        JButton purchaseOrderButton = new JButton("采购订单");
        JButton salesOrderButton = new JButton("销售订单");

        // 设置按钮大小一致
        Dimension buttonSize = new Dimension(200, 50);
        bookManagementButton.setPreferredSize(buttonSize);
        orderManagementButton.setPreferredSize(buttonSize);
        financeManagementButton.setPreferredSize(buttonSize);
        purchaseOrderButton.setPreferredSize(buttonSize);
        salesOrderButton.setPreferredSize(buttonSize);

        // 将按钮添加到左侧面板
        sidePanel.add(bookManagementButton);
        sidePanel.add(orderManagementButton);
        sidePanel.add(purchaseOrderButton);
        sidePanel.add(salesOrderButton);
        sidePanel.add(financeManagementButton);

        // 右侧内容面板，展示不同的功能区
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout()); // 使用CardLayout实现内容的切换

        JPanel bookPanel = new JPanel();
        bookPanel.add(new JLabel("图书管理内容"));

        JPanel orderPanel = new JPanel();
        orderPanel.add(new JLabel("订单管理内容"));

        JPanel financePanel = new JPanel();
        financePanel.add(new JLabel("财务管理内容"));

        JPanel purchaseOrderPanel = new JPanel();
        purchaseOrderPanel.add(new JLabel("采购订单内容"));

        JPanel salesOrderPanel = new JPanel();
        salesOrderPanel.add(new JLabel("销售订单内容"));

        contentPanel.add(bookPanel, "图书管理");
        contentPanel.add(orderPanel, "订单管理");
        contentPanel.add(financePanel, "财务管理");
        contentPanel.add(purchaseOrderPanel, "采购订单");
        contentPanel.add(salesOrderPanel, "销售订单");

        // 事件处理：点击左侧按钮时切换右侧内容
        bookManagementButton.addActionListener(e -> switchPanel("图书管理"));
        orderManagementButton.addActionListener(e -> switchPanel("订单管理"));
        financeManagementButton.addActionListener(e -> switchPanel("财务管理"));
        purchaseOrderButton.addActionListener(e -> switchPanel("采购订单"));
        salesOrderButton.addActionListener(e -> switchPanel("销售订单"));

        // 将左侧面板和右侧内容面板添加到主面板
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // 将主面板添加到框架中
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // 根据按钮点击切换显示的面板
    public void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
}
