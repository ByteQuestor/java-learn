package view;

import javax.swing.*;

import controller.StudentController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

// 新增面板类，构建用于输入新增学生信息的面板以及相关操作逻辑
public class AddPanel {

    private JPanel panel;
    private JTextField addNameField, addPhoneField, addAddressField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JSpinner birthDateSpinner;
    private JButton addButton;
    private StudentManagerView parentView;

    public AddPanel(StudentManagerView parentView) {
        this.parentView = parentView;
        initialize();
    }

    private void initialize() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // 学生姓名输入框
        addNameField = createTextFieldPanel(panel, "新增学生姓名：");

        // 性别单选框
        maleRadioButton = new JRadioButton("男");
        femaleRadioButton = new JRadioButton("女");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        panel.add(createPanelWithLabel("性别：", maleRadioButton, femaleRadioButton));

        // 出生日期选择器
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd");
        birthDateSpinner.setEditor(dateEditor);
        panel.add(createPanelWithLabel("出生日期：", birthDateSpinner));

        // 电话输入框
        addPhoneField = createTextFieldPanel(panel, "电话：");

        // 地址输入框
        addAddressField = createTextFieldPanel(panel, "地址：");

        // 提交按钮
        addButton = new JButton("提交");
        addButton.setPreferredSize(new Dimension(150, 40));
        panel.add(addButton);

        addButton.addActionListener(e -> handleAddButtonClick());
    }

    private JTextField createTextFieldPanel(JPanel panel, String label) {
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subPanel.add(new JLabel(label));
        JTextField textField = new JTextField(20);
        subPanel.add(textField);
        panel.add(subPanel);
        return textField;
    }

    private JPanel createPanelWithLabel(String label, JComponent... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        for (JComponent comp : components) {
            panel.add(comp);
        }
        return panel;
    }

    private void handleAddButtonClick() {
    	
    	System.out.print("点击了新增学生信息");
        String name = addNameField.getText();
        String gender = maleRadioButton.isSelected()? "男" : "女";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = (Date) birthDateSpinner.getValue();
        String formattedBirthDate = sdf.format(birthDate);
        String phone = addPhoneField.getText();
        String address = addAddressField.getText();
        
        //调试信息
        System.out.println("点击了新增学生信息");
        System.out.println("学生姓名: " + name);
        System.out.println("性别: " + gender);
        System.out.println("出生日期: " + formattedBirthDate);
        System.out.println("电话: " + phone);
        System.out.println("地址: " + address);
        StudentController controller = new StudentController(this.parentView);
    	controller.addStudent(null,name, gender, formattedBirthDate, phone, address);
        
        // 通过父视图调用控制器方法来添加学生信息
        parentView.updateStudentTable();
        clearAddForm();
    }

    private void clearAddForm() {
        addNameField.setText("");
        maleRadioButton.setSelected(true);
        birthDateSpinner.setValue(new Date());
        addPhoneField.setText("");
        addAddressField.setText("");
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
    public JPanel getPanel() {
        return panel;
    }
}