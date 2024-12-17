package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;

import controller.StudentController;

import java.util.Date;
import java.util.List;

public class StudentManagerView {

    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTable allStudentTable; // 添加 JTable 用于显示学生信息
    private JTable addStudentTable; // 添加 JTable 用于显示学生信息
    private JTable updateStudentTable; // 添加 JTable 用于显示学生信息
    private JTable deleteStudentTable; // 添加 JTable 用于显示学生信息

    // 下半部分修改框组件
    private JTextField studentIdField;
    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JTextField dobField;
    private JTextField phoneField;
    private JTextField addressField;

    // 提交按钮
    private JButton submitButton;

    public StudentManagerView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("NEW 学生信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // 导航面板，放在左边
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setPreferredSize(new Dimension(150, 600));

        JButton btnQuery = new JButton("工作桌面");
        JButton btnAdd = new JButton("新增信息");
        JButton btnUpdate = new JButton("修改信息");
        JButton btnDelete = new JButton("操作记录");

        navigationPanel.add(btnQuery);
        navigationPanel.add(btnAdd);
        navigationPanel.add(btnUpdate);
        navigationPanel.add(btnDelete);

        frame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        // 卡片布局，用于切换不同的面板
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        PanelFactory panelFactory = new PanelFactory();
        JPanel queryPanel = panelFactory.createPanel("工作桌面");
        JPanel addPanel = panelFactory.createPanel("新增信息");
        JPanel updatePanel = panelFactory.createPanel("修改信息");
        JPanel deletePanel = panelFactory.createPanel("操作记录");

        cardPanel.add(queryPanel, "query");
        cardPanel.add(addPanel, "add");
        cardPanel.add(updatePanel, "update");
        cardPanel.add(deletePanel, "delete");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("状态栏：系统正常运行", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);

        btnQuery.addActionListener(e -> cardLayout.show(cardPanel, "query"));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, "add"));
        btnUpdate.addActionListener(e -> cardLayout.show(cardPanel, "update"));
        btnDelete.addActionListener(e -> cardLayout.show(cardPanel, "delete"));

        // 创建上下分区
        JPanel queryContentPanel = new JPanel();
        queryContentPanel.setLayout(new BorderLayout());

        // 上半部分：显示数据的表格，设置为一个带滚动条的区域
        allStudentTable = new JTable();
        JScrollPane allScrollPane = new JScrollPane(allStudentTable);
        queryContentPanel.add(allScrollPane, BorderLayout.CENTER); // 数据表格放在上部

        // 下半部分：修改框
        JPanel editPanel = new JPanel(new GridLayout(7, 2)); // 使用 GridLayout 显示修改框
        editPanel.add(new JLabel("学生ID:"));
        studentIdField = new JTextField();
        studentIdField.setEditable(false); // 学生ID不可编辑
        editPanel.add(studentIdField);

        editPanel.add(new JLabel("姓名:"));
        nameField = new JTextField();
        editPanel.add(nameField);

        editPanel.add(new JLabel("性别:"));
        genderComboBox = new JComboBox<>(new String[] { "男", "女" });
        editPanel.add(genderComboBox);

        editPanel.add(new JLabel("出生日期:"));
        dobField = new JTextField();
        editPanel.add(dobField);

        editPanel.add(new JLabel("电话:"));
        phoneField = new JTextField();
        editPanel.add(phoneField);

        editPanel.add(new JLabel("地址:"));
        addressField = new JTextField();
        editPanel.add(addressField);

        // 提交修改按钮
        submitButton = new JButton("提交修改");
        editPanel.add(new JLabel());  // 添加一个空的占位标签
        editPanel.add(submitButton);

        queryContentPanel.add(editPanel, BorderLayout.SOUTH); // 修改框放在下半部分

        // 将queryContentPanel直接添加到cardPanel，而不再使用JSplitPane
        cardPanel.add(queryContentPanel, "query");

        // 以下代码保持不变
        addStudentTable = new JTable();
        JScrollPane addScrollPane = new JScrollPane(addStudentTable);
        addPanel.add(addScrollPane, BorderLayout.CENTER); // 将表格放入新增面板的中间部分

        updateStudentTable = new JTable();
        JScrollPane updateScrollPane = new JScrollPane(updateStudentTable);
        updatePanel.add(updateScrollPane, BorderLayout.CENTER); // 将表格放入修改面板的中间部分

        deleteStudentTable = new JTable();
        JScrollPane deleteScrollPane = new JScrollPane(deleteStudentTable);
        deletePanel.add(deleteScrollPane, BorderLayout.CENTER); // 将表格放入删除面板的中间部分

        // 添加表格行点击事件监听器
        allStudentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = allStudentTable.getSelectedRow();
                if (row != -1) {
                    // 获取选中行的数据
                    String studentId = (String) allStudentTable.getValueAt(row, 0);
                    String name = (String) allStudentTable.getValueAt(row, 1);
                    String gender = (String) allStudentTable.getValueAt(row, 2);
                    String dob = (String) allStudentTable.getValueAt(row, 3);
                    String phone = (String) allStudentTable.getValueAt(row, 4);
                    String address = (String) allStudentTable.getValueAt(row, 5);

                    // 将数据填充到修改框
                    studentIdField.setText(studentId);
                    nameField.setText(name);
                    genderComboBox.setSelectedItem(gender);
                    dobField.setText(dob);
                    phoneField.setText(phone);
                    addressField.setText(address);
                }
            }
        });
        addSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateButtonClick();  // 触发更新操作
            }
        });
    }

    // 渲染学生信息到表格
    public void displayStudents(List<String[]> students) {
        // 定义列名
        String[] columnNames = { "学生ID", "姓名", "性别", "出生日期", "电话", "地址" };

        // 设置表格模型，禁止编辑
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 禁止所有单元格编辑
            }
        };
        // 将学生数据添加到表格中
        for (String[] student : students) {
            model.addRow(student);
        }

        // 设置表格数据
        allStudentTable.setModel(model);
        addStudentTable.setModel(model);
        updateStudentTable.setModel(model);
        deleteStudentTable.setModel(model);
    }

    // 获取提交修改按钮的监听器
    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
    // 提交按钮
    
    // 从表单获取信息，并且提交数据库
    private void handleUpdateButtonClick() {
        String studentId = studentIdField.getText();
        String name = nameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();  // 使用 genderComboBox 获取性别
        String dob = dobField.getText();  // 获取出生日期
        String phone = phoneField.getText();
        String address = addressField.getText();
        // 将修改的信息通过控制器提交到数据库
        StudentController controller = new StudentController(this);
        controller.updateStudent(studentId, name, gender, dob, phone, address);
    }

}
