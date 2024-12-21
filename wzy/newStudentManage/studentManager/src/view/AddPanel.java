package view;

import javax.swing.*;

import controller.StudentController;
import controller.UserController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

// 新增面板类，构建用于输入新增学生信息的面板以及相关操作逻辑
public class AddPanel {

	private JPanel panel;
	private JTextField addUserNameField, addNameField, addPhoneField, addAddressField, addPasswordField;
	private JRadioButton maleRadioButton, femaleRadioButton, teacherRadioButton, adminRadioButton;
	private JSpinner birthDateSpinner;
	private JButton addButton;
	private StudentManagerView parentView;

	public AddPanel(StudentManagerView parentView, int role) {
		this.parentView = parentView;
		initialize(role);
	}

	private void initialize(int role) {
		if (role == 1) {
			createStudentForm();
		} else if (role == 2) {
			createUserForm();
		}
	}

	private void createStudentForm() {
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
		// 密码输入框
		addPasswordField = createTextFieldPanel(panel, "密码：");

		// 提交按钮
		addButton = new JButton("提交");
		addButton.setPreferredSize(new Dimension(150, 40));
		panel.add(addButton);

		addButton.addActionListener(e -> handleAddButtonClick());
	}

	private void createUserForm() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// 用户真实姓名输入框
		addUserNameField = createTextFieldPanel(panel, "新增用户真实姓名：");
		// 用户姓名输入框
		addNameField = createTextFieldPanel(panel, "新增用户账号：");

		// 角色单选框
		teacherRadioButton = new JRadioButton("1");
		adminRadioButton = new JRadioButton("2");
		ButtonGroup roleGroup = new ButtonGroup();
		roleGroup.add(teacherRadioButton);
		roleGroup.add(adminRadioButton);
		panel.add(createPanelWithLabel("角色：", teacherRadioButton, adminRadioButton));

		// 密码输入框
		addPasswordField = createTextFieldPanel(panel, "密码：");

		// 提交按钮
		addButton = new JButton("提交");
		addButton.setPreferredSize(new Dimension(150, 40));
		panel.add(addButton);

		addButton.addActionListener(e -> handleUserAddButtonClick());
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
		String gender = maleRadioButton.isSelected() ? "男" : "女";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = (Date) birthDateSpinner.getValue();
		String formattedBirthDate = sdf.format(birthDate);
		String phone = addPhoneField.getText();
		String address = addAddressField.getText();
		String password = addPasswordField.getText();
		// 调试信息
		System.out.println("点击了新增学生信息");
		System.out.println("学生姓名: " + name);
		System.out.println("性别: " + gender);
		System.out.println("出生日期: " + formattedBirthDate);
		System.out.println("电话: " + phone);
		System.out.println("地址: " + address);
		System.out.println("密码: " + password);
		StudentController controller = new StudentController(this.parentView);
		controller.addStudent(null, name, gender, formattedBirthDate, phone, address, password);

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
		addPasswordField.setText("");
	}

	private void clearUserAddForm() {
		addUserNameField.setText("");
		teacherRadioButton.setSelected(true);
		addNameField.setText("");
		addPasswordField.setText("");
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
		// 地址输入框
		addPasswordField = createTextFieldPanel(addPanel, "密码：");

		// 提交按钮
		addButton = new JButton("提交");
		addButton.setPreferredSize(new Dimension(150, 40));
		addPanel.add(addButton);

		addButton.addActionListener(e -> handleAddButtonClick());
	}

	private void handleUserAddButtonClick() {

		System.out.print("点击了新增用户信息");
		String userName = addUserNameField.getText();
		String name = addNameField.getText();
		String role = teacherRadioButton.isSelected() ? "1" : "2";
		String password = addPasswordField.getText();
		// 调试信息
		System.out.println("点击了新增用户信息");
		System.out.println("用户姓名: " + name);
		System.out.println("性别: " + role);
		System.out.println("密码: " + password);
		UserController controller = new UserController(this.parentView);
		controller.addUser(null, userName, name, role, password);

		// 通过父视图调用控制器方法来添加学生信息
		parentView.updateUserTable();
		clearUserAddForm();
	}

	// 新增用户表单
	private void addUserForm(JPanel addPanel) {
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		// 用户姓名输入框
		addUserNameField = createTextFieldPanel(addPanel, "新增用户真实姓名：");
		// 用户姓名输入框
		addNameField = createTextFieldPanel(addPanel, "新增用户姓名：");

		// 性别单选框
		maleRadioButton = new JRadioButton("男");
		femaleRadioButton = new JRadioButton("女");
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);
		addPanel.add(createPanelWithLabel("性别：", maleRadioButton, femaleRadioButton));
		// 角色单选框
		teacherRadioButton = new JRadioButton("老师");
		adminRadioButton = new JRadioButton("管理员");
		ButtonGroup roleGroup = new ButtonGroup();
		roleGroup.add(teacherRadioButton);
		roleGroup.add(adminRadioButton);
		addPanel.add(createPanelWithLabel("角色：", teacherRadioButton, adminRadioButton));
		// 出生日期选择器
		birthDateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd");
		birthDateSpinner.setEditor(dateEditor);
		addPanel.add(createPanelWithLabel("出生日期：", birthDateSpinner));

		// 电话输入框
		addPhoneField = createTextFieldPanel(addPanel, "电话：");

		// 地址输入框
		addAddressField = createTextFieldPanel(addPanel, "地址：");
		// 地址输入框
		addPasswordField = createTextFieldPanel(addPanel, "密码：");

		// 提交按钮
		addButton = new JButton("提交");
		addButton.setPreferredSize(new Dimension(150, 40));
		addPanel.add(addButton);

		addButton.addActionListener(e -> handleUserAddButtonClick());
	}

	public JPanel getPanel() {
		return panel;
	}
}