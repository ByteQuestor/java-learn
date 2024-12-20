package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.CourseController;
import controller.StudentController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 查询面板类，构建展示学生信息表格以及相关编辑功能的面板
public class QueryPanel {

	private JPanel panel;
	private JTable allStudentTable, allCourseTable;
	private JTextField editStudentIdField, editNameField, dobField, editPhoneField, editAddressField;
	private JComboBox<String> genderComboBox;
	private JButton submitButton, deleteButton;
	private int deleteID = -1, deleteRow = -1;
	private StudentManagerView parentView;

	public QueryPanel(StudentManagerView parentView, int role) {
		this.parentView = parentView;
		initialize(role);
	}

	private void initialize(int role) {
		panel = new JPanel(new BorderLayout());
		allStudentTable = new JTable();
		allCourseTable = new JTable();// 渲染课程表
		allStudentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 当用户点击表格行时，填充信息到修改表单
				int selectedRow = allStudentTable.getSelectedRow();
				System.out.print("selectedRow: " + selectedRow + "\n");
				if (selectedRow != -1) {
					editStudentIdField.setText(allStudentTable.getValueAt(selectedRow, 0).toString());
					editNameField.setText(allStudentTable.getValueAt(selectedRow, 1).toString());
					genderComboBox.setSelectedItem(allStudentTable.getValueAt(selectedRow, 2).toString());
					dobField.setText(allStudentTable.getValueAt(selectedRow, 3).toString());
					editPhoneField.setText(allStudentTable.getValueAt(selectedRow, 4).toString());
					editAddressField.setText(allStudentTable.getValueAt(selectedRow, 5).toString());
					// 获取要删除的学生的id
					String studentIdStr = allStudentTable.getValueAt(selectedRow, 0).toString();
					deleteRow = selectedRow;
					deleteID = Integer.parseInt(studentIdStr);
					System.out.print("deleteID  " + deleteID + "\n");
				}
			}
		});
		if (role == 0) {
			panel.add(new JScrollPane(allCourseTable), BorderLayout.CENTER);// 显示课程表
		} else if (role == 1) {
			panel.add(new JScrollPane(allStudentTable), BorderLayout.CENTER);

			JPanel editPanel = createEditPanel();
			panel.add(editPanel, BorderLayout.SOUTH);
		}

	}

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

		// 提交和删除按钮
		submitButton = new JButton("提交修改");
		deleteButton = new JButton("删除信息");
		editPanel.add(submitButton);
		editPanel.add(deleteButton);
		// 【删除按钮】监听器
		deleteButton.addActionListener(e -> handleDeleteButtonClick());

		// 【修改按钮】监听器
		submitButton.addActionListener(e -> handleUpdateButtonClick());

		return editPanel;

	}

	public JPanel getPanel() {
		return panel;
	}

	public void addSubmitListener(ActionListener listener) {
		submitButton.addActionListener(listener);
	}

	// 更新查询面板的学生列表
	public void updateStudentTable() {
		StudentController controller = new StudentController(this.parentView);
		controller.loadStudentData();
	}

	// 更新课程表
	public void updateCourseTable() {
		CourseController controller = new CourseController(this.parentView);
		controller.loadCourseData();
	}

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

	public void displayCourses(List<String[]> course) {
		String[] columnNames = { "课程ID", "课程名称", "课程代码", "任课老师" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		course.forEach(student -> model.addRow(student));
		allCourseTable.setModel(model);
	}

	// 处理【删除信息】点击事件
	private void handleDeleteButtonClick() {
		System.out.print("删除学生，id=" + deleteID + "\n");
		// String studentId = allStudentTable.getValueAt(deleteRow, 0).toString();
		if (deleteID != -1) {
			StudentController controller = new StudentController(this.parentView);
			String studentId = allStudentTable.getValueAt(deleteRow, 0).toString();
			System.out.print("String删除学生，id=" + studentId + "\n");
			controller.deleteStudent(studentId);
			updateStudentTable();
		} else {
			System.out.print("未选中\n");
		}
	}

	// 处理【提交修改】点击事件
	private void handleUpdateButtonClick() {
		String studentId = editStudentIdField.getText();
		String name = editNameField.getText();
		String gender = (String) genderComboBox.getSelectedItem();
		String dob = dobField.getText();
		String phone = editPhoneField.getText();
		String address = editAddressField.getText();

		StudentController controller = new StudentController(this.parentView);
		controller.updateStudent(studentId, name, gender, dob, phone, address);
		updateStudentTable();
	}
}