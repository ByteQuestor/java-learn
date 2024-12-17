package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class InfoManagementSystem {

    // 主界面框架
    private JFrame frame;
    private CardLayout cardLayout; // 用于切换不同的功能界面
    private JPanel cardPanel; // 存放不同功能模块的面板

    // 入口方法
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                InfoManagementSystem window = new InfoManagementSystem();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // 创建并展示界面
    public InfoManagementSystem() {
        initialize();
    }

    // 初始化界面
    private void initialize() {
        // 创建主框架
        frame = new JFrame("信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建主布局：使用 BorderLayout
        frame.getContentPane().setLayout(new BorderLayout());

        // 创建左侧导航栏面板，按钮垂直排列
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS)); // 使用垂直方向的布局
        navigationPanel.setPreferredSize(new Dimension(150, 600)); // 设置导航栏宽度
        JButton btnQuery = new JButton("信息查询");
        JButton btnAdd = new JButton("新增信息");
        JButton btnUpdate = new JButton("修改信息");
        JButton btnDelete = new JButton("删除信息");

        navigationPanel.add(btnQuery);
        navigationPanel.add(btnAdd);
        navigationPanel.add(btnUpdate);
        navigationPanel.add(btnDelete);

        // 为按钮添加事件监听器
        btnQuery.addActionListener(e -> cardLayout.show(cardPanel, "query"));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, "add"));
        btnUpdate.addActionListener(e -> cardLayout.show(cardPanel, "update"));
        btnDelete.addActionListener(e -> cardLayout.show(cardPanel, "delete"));

        // 将左侧导航栏添加到左边
        frame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        // 创建功能面板：使用 CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 创建一个边框，用于整个功能面板
        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        cardPanel.setBorder(panelBorder);

        // 信息查询面板
        JPanel queryPanel = createPanel("信息查询");
        // 可以在此处添加查询功能相关的组件

        // 新增信息面板
        JPanel addPanel = createPanel("新增信息");
        // 可以在此处添加新增功能相关的组件

        // 修改信息面板
        JPanel updatePanel = createPanel("修改信息");
        // 可以在此处添加修改功能相关的组件

        // 删除信息面板
        JPanel deletePanel = createPanel("删除信息");
        // 可以在此处添加删除功能相关的组件

        // 将功能面板加入到cardPanel
        cardPanel.add(queryPanel, "query");
        cardPanel.add(addPanel, "add");
        cardPanel.add(updatePanel, "update");
        cardPanel.add(deletePanel, "delete");

        // 将cardPanel添加到右侧
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // 状态栏
        JLabel statusLabel = new JLabel("状态栏：系统正常运行", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    // 创建带边框的面板
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 创建一个边框，用于每个功能面板
        Border panelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title);
        panel.setBorder(panelBorder);

        // 在面板上添加内容
        panel.add(new JLabel(title + "界面"), BorderLayout.CENTER);

        return panel;
    }
}
