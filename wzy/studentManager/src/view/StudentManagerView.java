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
	private JTable allStudentTable, addStudentTable, updateStudentTable, deleteStudentTable; // 添加 JTable 用于显示学生信息

	// 下半部分修改框组件
	private JTextField editStudentIdField, editNameField, dobField, editPhoneField, editAddressField;
	private JComboBox<String> genderComboBox;
	// 新增信息
	private JRadioButton maleRadioButton, femaleRadioButton;
	private JSpinner birthDateSpinner; // 使用 JSpinner 来选择日期
	private JTextField addStudentIdField, addNameField, addDobField, addPhoneField, addAddressField;
	// 提交按钮
	private JButton submitButton, deleteButton;
	// 新增按钮
	private JButton addButton;
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

		JButton btnQuery = new JButton("工作桌面");
		JButton btnAdd = new JButton("新增信息");
		JButton btnUpdate = new JButton("课表信息");
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
		JPanel queryPanel = panelFactory.createPanel("欢迎界面");
		JPanel addPanel = panelFactory.createPanel("新增信息");
		JPanel updatePanel = panelFactory.createPanel("开发中");
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
		JPanel editPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // 显示个数4 、

		editPanel.add(new JLabel("学生ID:"));
		editPanel.setSize(10, 10);
		editStudentIdField = new JTextField();
		editStudentIdField.setEditable(false); // 学生ID不可编辑
		editStudentIdField.setPreferredSize(new Dimension(20, 30)); // 设置大小，不过好像没有用
		editPanel.add(editStudentIdField);

		editPanel.add(new JLabel("姓名:"));
		editNameField = new JTextField();
		editPanel.add(editNameField);

		editPanel.add(new JLabel("性别:"));
		genderComboBox = new JComboBox<>(new String[] { "男", "女" });
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

		// 提交修改按钮
		submitButton = new JButton("提交修改");
		editPanel.add(new JLabel()); // 添加一个空的占位标签
		editPanel.add(submitButton);
		// 删除按钮
		deleteButton = new JButton("删除信息");
		editPanel.add(new JLabel()); // 添加一个空的占位标签
		editPanel.add(deleteButton);
		queryContentPanel.add(editPanel, BorderLayout.SOUTH); // 修改框放在下半部分

		// 将queryContentPanel直接添加到cardPanel
		cardPanel.add(queryContentPanel, "query");

		updateStudentTable = new JTable();
		JScrollPane updateScrollPane = new JScrollPane(updateStudentTable);
		updatePanel.add(updateScrollPane, BorderLayout.CENTER); // 将表格放入修改面板的中间部分

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
					editStudentIdField.setText(studentId);
					editNameField.setText(name);
					genderComboBox.setSelectedItem(gender);
					dobField.setText(dob);
					editPhoneField.setText(phone);
					editAddressField.setText(address);
					deleteRow = Integer.parseInt(studentId);
				}
			}
		});
		// 添加表格行点击事件监听器
		addSubmitListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleUpdateButtonClick(); // 触发更新操作
			}
		});
		// 新增信息面板
		addStudentForm(addPanel);

		// 监听删除按钮点击事件
		deleteButton.addActionListener(e -> handleDeleteButtonClick());
	}

	// 新增信息按钮
	private void addStudentForm(JPanel queryContentPanel) {
		queryContentPanel.setLayout(new BoxLayout(queryContentPanel, BoxLayout.Y_AXIS));

//		// 学生ID输入框
//		// 获取数据库中最大学生ID，并自动填充加1后的ID
//		// 将新增信息通过控制器添加到数据库
//		StudentController controller = new StudentController(this);
//		int newStudentId = controller.getMaxStudentId(); // 获取最大ID并加1
//		addStudentIdField.setText(String.valueOf(newStudentId)); // 填充到表单
//		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		idPanel.add(new JLabel("新增学生ID："));
//		addStudentIdField = new JTextField(20);
//		idPanel.add(addStudentIdField);
//		queryContentPanel.add(idPanel);

		// 姓名输入框
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(new JLabel("新增学生姓名："));
		addNameField = new JTextField(20);
		namePanel.add(addNameField);
		queryContentPanel.add(namePanel);

		// 性别单选框
		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		genderPanel.add(new JLabel("性别："));
		maleRadioButton = new JRadioButton("男");
		femaleRadioButton = new JRadioButton("女");
		ButtonGroup genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);
		genderPanel.add(maleRadioButton);
		genderPanel.add(femaleRadioButton);
		queryContentPanel.add(genderPanel);

		// 出生日期选择器（使用 JSpinner）
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		datePanel.add(new JLabel("出生日期："));
		birthDateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd");
		birthDateSpinner.setEditor(dateEditor);
		datePanel.add(birthDateSpinner);
		queryContentPanel.add(datePanel);

		// 电话输入框
		JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		phonePanel.add(new JLabel("电话："));
		addPhoneField = new JTextField(20);
		phonePanel.add(addPhoneField);
		queryContentPanel.add(phonePanel);

		// 地址输入框
		JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		addressPanel.add(new JLabel("地址："));
		addAddressField = new JTextField(20);
		addressPanel.add(addAddressField);
		queryContentPanel.add(addressPanel);

		// 提交按钮
		addButton = new JButton("提交");
		addButton.setPreferredSize(new Dimension(150, 40)); // 设置按钮大小
		queryContentPanel.add(addButton);

		// 提交按钮监听器
		addButton.addActionListener(e -> handleAddButtonClick());
	}

	private void handleAddButtonClick() {
		// String studentId = addStudentIdField.getText();
		StudentController addController = new StudentController(this);
		System.out.print(Integer.toString(addController.getMaxStudentId()));
		String studentId = null;// 让数据库自动处理id号
		String name = addNameField.getText();
		String gender = maleRadioButton.isSelected() ? "男" : "女";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = (Date) birthDateSpinner.getValue();
		String formattedBirthDate = sdf.format(birthDate);
		String phone = addPhoneField.getText();
		String address = addAddressField.getText();

		// 将新增信息通过控制器添加到数据库
		StudentController controller = new StudentController(this);
		controller.addStudent(studentId, name, gender, formattedBirthDate, phone, address);
		// 更新表格
		updateStudentTable();
		// 清空输入框
		// addStudentIdField.setText("");
		editNameField.setText("");
		maleRadioButton.setSelected(true); // 默认选中男
		birthDateSpinner.setValue(new Date()); // 重置日期选择器为当前日期
		addPhoneField.setText("");
		addAddressField.setText("");
	}

	private void handleDeleteButtonClick() {
		// int selectedRow = deleteStudentTable.getSelectedRow();
		if (deleteRow != -1) {
			StudentController controller = new StudentController(this);
			System.out.print(deleteRow);
			controller.deleteStudent(Integer.toString(deleteRow));

			// 更新表格
			updateStudentTable();
		} else {
			JOptionPane.showMessageDialog(frame, "请选择要删除的学生", "删除失败", JOptionPane.WARNING_MESSAGE);
		}
	}

	// 更新查询面板的学生列表
	public void updateStudentTable() {
		// 获取所有学生信息
		StudentController controller = new StudentController(this);
		controller.loadStudentData(); // 获取学生数据
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
	}

	public JFrame getFrame() {
		return frame;
	}

	// 提交按钮
	// 获取提交修改按钮的监听器
	public void addSubmitListener(ActionListener listener) {
		submitButton.addActionListener(listener);
	}

	// 从表单获取信息，并且提交数据库
	private void handleUpdateButtonClick() {
		String studentId = editStudentIdField.getText();
		String name = editNameField.getText();
		String gender = (String) genderComboBox.getSelectedItem(); // 使用 genderComboBox 获取性别
		String dob = dobField.getText(); // 获取出生日期
		String phone = editPhoneField.getText();
		String address = editAddressField.getText();
		// 将修改的信息通过控制器提交到数据库
		StudentController controller = new StudentController(this);
		controller.updateStudent(studentId, name, gender, dob, phone, address);
		// 更新表格
		updateStudentTable();
	}
}