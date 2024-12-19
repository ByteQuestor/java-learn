package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import controller.StudentController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentManagerView {

    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTable allStudentTable;
    private JTextField editStudentIdField, editNameField, dobField, editPhoneField, editAddressField;
    private JTextField addNameField, addPhoneField, addAddressField;
    private JComboBox<String> genderComboBox;
    private JButton submitButton, deleteButton, addButton;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JSpinner birthDateSpinner;
    private int deleteRow = -1;

    public StudentManagerView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("学生信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // 导航面板，放在左边
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setPreferredSize(new Dimension(150, 600));
        JButton btnQuery = createNavButton("工作桌面", "query");
        JButton btnAdd = createNavButton("新增信息", "add");
        JButton btnUpdate = createNavButton("课表信息", "update");
        JButton btnDelete = createNavButton("操作记录", "delete");

        navigationPanel.add(btnQuery);
        navigationPanel.add(btnAdd);
        navigationPanel.add(btnUpdate);
        navigationPanel.add(btnDelete);
        frame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        // 卡片布局，用于切换不同的面板
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        PanelFactory panelFactory = new PanelFactory();
        JPanel queryPanel = panelFactory.createPanel("欢迎界面");
        JPanel addPanel = panelFactory.createPanel("新增信息");
        JPanel updatePanel = panelFactory.createPanel("开发中");
        JPanel deletePanel = panelFactory.createPanel("操作记录");

        cardPanel.add(queryPanel, "query");
        cardPanel.add(addPanel, "add");
        cardPanel.add(updatePanel, "update");
        cardPanel.add(deletePanel, "delete");
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // 状态栏
        JLabel statusLabel = new JLabel("状态栏：系统正常运行", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);

        // 处理【导航按钮】点击
        btnQuery.addActionListener(e -> cardLayout.show(cardPanel, "query"));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, "add"));
        btnUpdate.addActionListener(e -> cardLayout.show(cardPanel, "update"));
        btnDelete.addActionListener(e -> cardLayout.show(cardPanel, "delete"));

        // 查询面板：包含表格
        JPanel queryContentPanel = new JPanel(new BorderLayout());
        allStudentTable = new JTable();
        allStudentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 当用户点击表格行时，填充信息到修改表单
                int selectedRow = allStudentTable.getSelectedRow();
                if (selectedRow != -1) {
                    editStudentIdField.setText(allStudentTable.getValueAt(selectedRow, 0).toString());
                    editNameField.setText(allStudentTable.getValueAt(selectedRow, 1).toString());
                    genderComboBox.setSelectedItem(allStudentTable.getValueAt(selectedRow, 2).toString());
                    dobField.setText(allStudentTable.getValueAt(selectedRow, 3).toString());
                    editPhoneField.setText(allStudentTable.getValueAt(selectedRow, 4).toString());
                    editAddressField.setText(allStudentTable.getValueAt(selectedRow, 5).toString());
                    deleteRow = selectedRow;
                }
            }
        });
        queryContentPanel.add(new JScrollPane(allStudentTable), BorderLayout.CENTER);
        JPanel editPanel = createEditPanel();
        queryContentPanel.add(editPanel, BorderLayout.SOUTH);
        cardPanel.add(queryContentPanel, "query");

        // 新增学生面板
        JPanel addStudentPanel = new JPanel();
        addStudentForm(addStudentPanel);
        cardPanel.add(addStudentPanel, "add");

        // 【修改按钮】监听器
        submitButton.addActionListener(e -> handleUpdateButtonClick());

        // 【删除按钮】监听器
        deleteButton.addActionListener(e -> handleDeleteButtonClick());
    }

    // 创建导航按钮
    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        return button;
    }

    // 创建学生信息编辑面板
    private JPanel createEditPanel() {
        JPanel editPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        // 添加编辑框
        editPanel.add(new JLabel("学生ID:"));
        editStudentIdField = new JTextField();
        editStudentIdField.setEditable(false);
        editPanel.add(editStudentIdField);

        editPanel.add(new JLabel("姓名:"));
        editNameField = new JTextField();
        editPanel.add(editNameField);

        editPanel.add(new JLabel("性别:"));
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        editPanel.add(genderComboBox);

        editPanel.add(new JLabel("出生日期:"));
        dobField = new JTextField();
        dobField.setEditable(false);
        editPanel.add(dobField);

        editPanel.add(new JLabel("电话:"));
        editPhoneField = new JTextField();
        editPanel.add(editPhoneField);

        editPanel.add(new JLabel("地址:"));
        editAddressField = new JTextField();
        editPanel.add(editAddressField);

        // 提交和删除按钮
        submitButton = new JButton("提交修改");
        deleteButton = new JButton("删除信息");
        editPanel.add(submitButton);
        editPanel.add(deleteButton);
        return editPanel;
    }

    // 新增学生表单
    private void addStudentForm(JPanel addPanel) {
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        // 学生姓名输入框
        addNameField = createTextFieldPanel(addPanel, "新增学生姓名：");

        // 性别单选框
        maleRadioButton = new JRadioButton("男");
        femaleRadioButton = new JRadioButton("女");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        addPanel.add(createPanelWithLabel("性别：", maleRadioButton, femaleRadioButton));

        // 出生日期选择器
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd");
        birthDateSpinner.setEditor(dateEditor);
        addPanel.add(createPanelWithLabel("出生日期：", birthDateSpinner));

        // 电话输入框
        addPhoneField = createTextFieldPanel(addPanel, "电话：");

        // 地址输入框
        addAddressField = createTextFieldPanel(addPanel, "地址：");

        // 提交按钮
        addButton = new JButton("提交");
        addButton.setPreferredSize(new Dimension(150, 40));
        addPanel.add(addButton);

        addButton.addActionListener(e -> handleAddButtonClick());
    }

    // 创建带标签的文本框
    private JTextField createTextFieldPanel(JPanel panel, String label) {
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subPanel.add(new JLabel(label));
        JTextField textField = new JTextField(20);
        subPanel.add(textField);
        panel.add(subPanel);
        return textField;
    }

    // 创建带标签的面板
    private JPanel createPanelWithLabel(String label, JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        for (JComponent comp : components) {
            panel.add(comp);
        }
        return panel;
    }

    // 处理【新增按钮】点击事件
    private void handleAddButtonClick() {
        String name = addNameField.getText();
        String gender = maleRadioButton.isSelected() ? "男" : "女";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = (Date) birthDateSpinner.getValue();
        String formattedBirthDate = sdf.format(birthDate);
        String phone = addPhoneField.getText();
        String address = addAddressField.getText();

        // 将新增信息通过控制器添加到数据库
        StudentController controller = new StudentController(this);
        controller.addStudent(null,name, gender, formattedBirthDate, phone, address);
        updateStudentTable();
        clearAddForm();
    }

    // 清空新增表单
    private void clearAddForm() {
        addNameField.setText("");
        maleRadioButton.setSelected(true);
        birthDateSpinner.setValue(new Date());
        addPhoneField.setText("");
        addAddressField.setText("");
    }

    // 处理【删除按钮】点击事件
    private void handleDeleteButtonClick() {
        if (deleteRow != -1) {
            StudentController controller = new StudentController(this);
            String studentId = allStudentTable.getValueAt(deleteRow, 0).toString();
            controller.deleteStudent(studentId);
            updateStudentTable();
        } else {
            JOptionPane.showMessageDialog(frame, "请选择要删除的学生", "删除失败", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 更新查询面板的学生列表
    public void updateStudentTable() {
        StudentController controller = new StudentController(this);
        controller.loadStudentData();
    }

    // 渲染学生信息到表格
    public void displayStudents(List<String[]> students) {
        String[] columnNames = { "学生ID", "姓名", "性别", "出生日期", "电话", "地址" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        students.forEach(student -> model.addRow(student));
        allStudentTable.setModel(model);
    }

    public JFrame getFrame() {
        return frame;
    }

    // 提交按钮
    public void addSubmitListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    // 更新学生信息
    private void handleUpdateButtonClick() {
        String studentId = editStudentIdField.getText();
        String name = editNameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String dob = dobField.getText();
        String phone = editPhoneField.getText();
        String address = editAddressField.getText();

        StudentController controller = new StudentController(this);
        controller.updateStudent(studentId, name, gender, dob, phone, address);
        updateStudentTable();
    }
}
