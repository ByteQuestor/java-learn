package one;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagementSystem extends JFrame {

    // 模拟图书数据模型，实际中可能从数据库获取
    private String[][] bookData = {
            {"1", "Java核心技术", "Cay Horstmann", "机械工业出版社"},
            {"2", "Effective Java", "Joshua Bloch", "机械工业出版社"}
    };
    private String[] columnNames = {"编号", "书名", "作者", "出版社"};

    public LibraryManagementSystem() {
        // 设置窗口标题
        setTitle("图书管理系统");
        // 设置窗口大小
        setSize(800, 600);
        // 设置窗口关闭时的默认操作（这里是退出程序）
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 整体布局
        initLayout();

        // 使窗口可见
        setVisible(true);
    }

    private void initLayout() {
        // 1. 创建菜单栏
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // 2. 创建图书信息显示表格
        JTable bookTable = createBookTable();
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // 3. 创建操作按钮区域
        JPanel buttonPanel = createButtonPanel();

        // 使用边界布局将各部分添加到主窗口
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 创建"图书管理"菜单
        JMenu bookMenu = new JMenu("图书管理");

        // 添加菜单项及事件监听器
        JMenuItem addBookMenuItem = new JMenuItem("添加图书");
        addBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 这里可以弹出添加图书的对话框，后续完善
                JOptionPane.showMessageDialog(null, "添加图书功能待实现");
            }
        });

        JMenuItem deleteBookMenuItem = new JMenuItem("删除图书");
        deleteBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 这里可以实现删除图书逻辑，后续完善
                JOptionPane.showMessageDialog(null, "删除图书功能待实现");
            }
        });

        bookMenu.add(addBookMenuItem);
        bookMenu.add(deleteBookMenuItem);

        menuBar.add(bookMenu);

        return menuBar;
    }

    private JTable createBookTable() {
        JTable table = new JTable(bookData, columnNames);
        return table;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "添加图书功能待细化");
            }
        });

        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "删除图书功能待细化");
            }
        });

        panel.add(addButton);
        panel.add(deleteButton);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystem();
            }
        });
    }
}
