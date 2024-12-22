package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.CourseController;
import controller.StudentController;
import controller.UserController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 查询面板类，构建展示学生信息表格以及相关编辑功能的面板
public class QueryPanel {

	private JPanel panel;
	private JTable allStudentTable, allCourseTable, allUserTable;
	private JTextField 	editStudentIdField, 
						editSourceField,
						editNameField, 
						editRealNameField, 
						dobField, 
						editPhoneField, 
						editAddressField,
						editPasswordField;
	private JComboBox<String> genderComboBox, roleComboBox;
	private JButton submitButton, deleteButton, editPasswordButton;
	private int deleteID = -1, deleteRow = -1, deleteUserRow = -1, deleteUserID = -1;
	private StudentManagerView parentView;
	public int tableStudentId,tableCourseId;
	public String editStudentName,editStudentPassword;
	public QueryPanel(StudentManagerView parentView, int role, String name, String password) {
		this.parentView = parentView;
		initialize(role, name, password);
	}

	private void initialize(int role, String name, String password) {
		this.editStudentName = name;
		this.editStudentPassword = password;
		panel = new JPanel(new BorderLayout());
		allStudentTable = new JTable();
		allCourseTable = new JTable();// 渲染课程表
		allUserTable = new JTable();// 渲染用户表
		allStudentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("我是老师\n");
				if (role == 1) {
					// 当用户点击表格行时，填充信息到修改表单
					System.out.print("我是老师\n");
					int selectedRow = allStudentTable.getSelectedRow();
					System.out.print("selectedRow: " + selectedRow + "\n");
					editStudentIdField.setText(allStudentTable.getValueAt(selectedRow, 0).toString());
					editNameField.setText(allStudentTable.getValueAt(selectedRow, 1).toString());
					genderComboBox.setSelectedItem(allStudentTable.getValueAt(selectedRow, 2).toString());
					dobField.setText(allStudentTable.getValueAt(selectedRow, 3).toString());
					editPhoneField.setText(allStudentTable.getValueAt(selectedRow, 4).toString());
					editAddressField.setText(allStudentTable.getValueAt(selectedRow, 5).toString());
					editPasswordField.setText(allStudentTable.getValueAt(selectedRow, 6).toString());
					// 获取要删除的学生的id
					String studentIdStr = allStudentTable.getValueAt(selectedRow, 0).toString();
					deleteRow = selectedRow;
					deleteID = Integer.parseInt(studentIdStr);
					System.out.print("deleteID  " + deleteID + "\n");
				}

			}
		});
		allUserTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 当管理员点击表格行时，填充信息到修改表单
				System.out.print("我是管理员\n");
				int selectedRow = allUserTable.getSelectedRow();
				System.out.print("selectedRow: " + selectedRow + "\n");
				if (selectedRow != -1) {
					editStudentIdField.setText(allUserTable.getValueAt(selectedRow, 0).toString());
					editRealNameField.setText(allUserTable.getValueAt(selectedRow, 1).toString());
					editNameField.setText(allUserTable.getValueAt(selectedRow, 2).toString());
					roleComboBox.setSelectedItem(allUserTable.getValueAt(selectedRow, 4).toString());
					editPasswordField.setText(allUserTable.getValueAt(selectedRow, 3).toString());
					// 获取要删除的学生的id
					String userIdStr = allUserTable.getValueAt(selectedRow, 0).toString();
					deleteUserRow = selectedRow;
					deleteUserID = Integer.parseInt(userIdStr);
					System.out.print("deleteUserID  " + deleteUserID + "\n");
				}
			}
		});
		allCourseTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 当学生点击表格行时，填充信息到修改表单
				System.out.print("我是学生的课程表\n");
				int selectedRow = allCourseTable.getSelectedRow();
				System.out.print("selectedRow: " + selectedRow + "\n");
				System.out.print("selectedId: " + allCourseTable.getValueAt(selectedRow, 0).toString() + "\n");
				if (selectedRow != -1) {
					//editStudentIdField.setText(allCourseTable.getValueAt(selectedRow, 0).toString());
					//editRealNameField.setText(allCourseTable.getValueAt(selectedRow, 1).toString());
					//editNameField.setText(allCourseTable.getValueAt(selectedRow, 2).toString());
					String studentId = editStudentIdField.getText();
					CourseController courseController = new CourseController(null);
					double source = courseController.showCurseSource(Integer.parseInt(studentId), Integer.parseInt(allCourseTable.getValueAt(selectedRow, 0).toString()));
					editSourceField.setText(String.valueOf(source));
					//生日框爆改课程名称框
					dobField.setText(allCourseTable.getValueAt(selectedRow, 1).toString());
					//地址框爆改任课老师
					editAddressField.setText(allCourseTable.getValueAt(selectedRow, 3).toString());
					//电话框爆改课程代码
					editPhoneField.setText(allCourseTable.getValueAt(selectedRow, 2).toString());
					System.out.print("选择的: " + allCourseTable.getValueAt(selectedRow, 0).toString() + "\n");
					System.out.print("选择的: " + allCourseTable.getValueAt(selectedRow, 1).toString() + "\n");
					System.out.print("选择的: " + allCourseTable.getValueAt(selectedRow, 2).toString() + "\n");
					System.out.print("选择的: " + allCourseTable.getValueAt(selectedRow, 3).toString() + "\n");
					// 获取要删除的学生的id
					String userIdStr = allCourseTable.getValueAt(selectedRow, 0).toString();
					deleteUserRow = selectedRow;
					deleteUserID = Integer.parseInt(userIdStr);
					System.out.print("deleteUserID  " + deleteUserID + "\n");
				}
			}
		});
		if (role == 0) {
			panel.add(new JScrollPane(allCourseTable), BorderLayout.CENTER);// 显示课程表
			// 为了给学生视图渲染信息
			StudentController studentController = new StudentController(null);
			List<String[]> studentInfo = studentController.getStudentByNameAndPassword(name, password);
			List<String[]> courceInfo = studentController.getStudentByNameAndPassword(name, password);
			
			System.out.println("查询的学生信息：");
			System.out.println(studentInfo.get(0)[0]);
			System.out.println(studentInfo.get(0)[1]);
			System.out.println(studentInfo.get(0)[2]);
			System.out.println(studentInfo.get(0)[3]);
			System.out.println(studentInfo.get(0)[4]);
			//this.tableStudentId = Integer.parseInt(studentInfo.get(0)[0]);
			
			JPanel editPanel = createSearchPanel(
					studentInfo.get(0)[0],
					this.editStudentName, 
					this.editStudentPassword, 
					"95.2",
					studentInfo.get(0)[2], 
					studentInfo.get(0)[3], 
					studentInfo.get(0)[4]	);
			panel.add(editPanel, BorderLayout.SOUTH);
		} else if (role == 1) {
			panel.add(new JScrollPane(allStudentTable), BorderLayout.CENTER);

			JPanel editPanel = createEditPanel();
			panel.add(editPanel, BorderLayout.SOUTH);
		} else if (role == 2) {
			panel.add(new JScrollPane(allUserTable), BorderLayout.CENTER);

			JPanel userEditPanel = createUserEditPanel();
			panel.add(userEditPanel, BorderLayout.SOUTH);
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

		editPanel.add(new JLabel("密码:"));
		editPasswordField = new JTextField();
		editPanel.add(editPasswordField);

		// 提交和删除按钮
		submitButton = new JButton("提交修改");
		deleteButton = new JButton("删除信息");
		editPanel.add(submitButton);
		editPanel.add(deleteButton);
		// 【删除按钮】监听器
		deleteButton.addActionListener(e -> handleDeleteButtonClick(1));

		// 【修改按钮】监听器
		submitButton.addActionListener(e -> handleUpdateButtonClick(1));

		return editPanel;

	}

	private JPanel createUserEditPanel() {
		JPanel editPanel = new JPanel(new GridLayout(2, 2, 5, 5));

		// 添加编辑框
		editPanel.add(new JLabel("用户ID:"));
		editStudentIdField = new JTextField();
		editStudentIdField.setEditable(false);
		editPanel.add(editStudentIdField);
		// 添加实名编辑框
		editPanel.add(new JLabel("姓名:"));
		editRealNameField = new JTextField();
		editPanel.add(editRealNameField);

		// 添加账号编辑框
		editPanel.add(new JLabel("账号:"));
		editNameField = new JTextField();
		editPanel.add(editNameField);

		editPanel.add(new JLabel("角色:"));
		roleComboBox = new JComboBox<>(new String[] { "老师", "管理员" });
		editPanel.add(roleComboBox);

		editPanel.add(new JLabel("密码:"));
		editPasswordField = new JTextField();
		editPanel.add(editPasswordField);

		// 提交和删除按钮
		submitButton = new JButton("提交修改");
		deleteButton = new JButton("删除信息");
		editPanel.add(submitButton);
		editPanel.add(deleteButton);
		// 【删除按钮】监听器
		deleteButton.addActionListener(e -> handleDeleteButtonClick(2));

		// 【修改按钮】监听器
		submitButton.addActionListener(e -> handleUpdateButtonClick(2));

		return editPanel;

	}

	// 创建查询面板

	// 添加查询按钮
	private JPanel createSearchPanel(
			String id, 
			String name, 
			String password, 
			String gender, 
			String brith, 
			String phone,
			String address) {
		JPanel searchPanel = new JPanel(new GridLayout(4, 2, 5, 5));

		// 添加编辑框
		searchPanel.add(new JLabel("学生ID:"));
		editStudentIdField = new JTextField();
		editStudentIdField.setText(id);
		editStudentIdField.setEditable(false);
		searchPanel.add(editStudentIdField);

		searchPanel.add(new JLabel("姓名:"));
		editNameField = new JTextField();
		editNameField.setText(name);
		editNameField.setEditable(false);
		searchPanel.add(editNameField);
		
		//生日框爆改课程名称框
		searchPanel.add(new JLabel("课程名称:"));
		dobField = new JTextField();
		dobField.setText(brith);
		dobField.setEditable(false);
		searchPanel.add(dobField);
		//电话框爆改课程代码
		searchPanel.add(new JLabel("课程代码:"));
		editPhoneField = new JTextField();
		editPhoneField.setText(phone);
		editPhoneField.setEditable(false);
		searchPanel.add(editPhoneField);

		//地址框爆改任课老师
		searchPanel.add(new JLabel("任课老师:"));
		editAddressField = new JTextField();
		editAddressField.setText(address);
		editAddressField.setEditable(false);
		searchPanel.add(editAddressField);
		
		searchPanel.add(new JLabel("成绩:"));
		editSourceField = new JTextField();
		//genderComboBox = new JComboBox<>(new String[] { "男", "女" });
		editSourceField.setText("99999999");
		editSourceField.setEditable(false);
		searchPanel.add(editSourceField);	

		

		searchPanel.add(new JLabel("密码:"));
		editPasswordField = new JTextField();
		editPasswordField.setText(password);
		searchPanel.add(editPasswordField);

		// 提交和删除按钮
		editPasswordButton = new JButton("提交修改");
		deleteButton = new JButton("删除信息");
		searchPanel.add(editPasswordButton);
		searchPanel.add(deleteButton);
		// 【删除按钮】监听器
		editPasswordButton.addActionListener(e -> handleEditPasswordButton());

		// 【修改按钮】监听器
		editPasswordButton.addActionListener(e -> handleUpdateButtonClick(0));

		return searchPanel;

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

	// 更新用户表
	public void updateUserTable() {
		UserController controller = new UserController(this.parentView);
		controller.loadUserData();
	}

	public void displayStudents(List<String[]> students) {
		String[] columnNames = { "学生ID", "姓名", "性别", "出生日期", "电话", "地址", "密码" };
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

	public void displayUsers(List<String[]> users) {
		String[] columnNames = { "用户ID", "用户实名", "用户账号", "用户密码", "角色" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//users.forEach(student -> model.addRow(student));
		
		// 遍历用户数据并转换角色值
	    users.forEach(user -> {
	        // 获取角色数字（角色是users数组中的第5个元素）
	        String role = user[4];
	        if ("1".equals(role)) {
	            user[4] = "老师";  // 将数字1渲染为“老师”
	        } else if ("2".equals(role)) {
	            user[4] = "管理员";  // 将数字2渲染为“管理员”
	        }
	        
	        model.addRow(user);  // 添加到表格模型中
	    });
		allUserTable.setModel(model);
	}

	// 处理【删除信息】点击事件
	private void handleDeleteButtonClick(int role) {
		if (role == 1) {
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
		} else if (role == 2) {
			System.out.print("删除用户，id=" + deleteUserID + "\n");
			// String studentId = allStudentTable.getValueAt(deleteRow, 0).toString();
			if (deleteUserID != -1) {
				UserController controller = new UserController(this.parentView);
				String userId = allUserTable.getValueAt(deleteUserRow, 0).toString();
				System.out.print("String 删除用户，id=" + userId + "\n");
				controller.deleteUser(userId);
				updateUserTable();

			} else {
				System.out.print("未选中\n");
			}
		}

	}

	// 处理【提交修改】点击事件
	private void handleUpdateButtonClick(int role) {
		if(role == 1) {
			String studentId = editStudentIdField.getText();
			String name = editNameField.getText();
			String gender = (String) genderComboBox.getSelectedItem();
			String dob = dobField.getText();
			String phone = editPhoneField.getText();
			String address = editAddressField.getText();
			String password = editPasswordField.getText();
			StudentController controller = new StudentController(this.parentView);
			controller.updateStudent(studentId, name, gender, dob, phone, address, password);
			updateStudentTable();
		}else if(role ==2) {
			//管理员面板的修改事件
			String userId = editStudentIdField.getText();
			String realname = editRealNameField.getText();
			String name = editNameField.getText();
			String l_role = (String) roleComboBox.getSelectedItem();
			String password = editPasswordField.getText();
			UserController controller = new UserController(this.parentView);
			System.out.print("角色讲修改为：" + l_role);
			if(l_role.equals("老师")) {
				System.out.print("\n插入1\n");
				controller.updateUser(userId, name, realname, "1", password);
			}else if(l_role.equals("管理员")) {
				System.out.print("\n插入2\n");
				controller.updateUser(userId, name, realname, "2", password);
			}
			updateUserTable();
		}
	}

	// 处理【修改密码】点击事件
	private void handleEditPasswordButton() {
		String studentId = editStudentIdField.getText();
		String name = editNameField.getText();
		String gender = (String) genderComboBox.getSelectedItem();
		String dob = dobField.getText();
		String phone = editPhoneField.getText();
		String address = editAddressField.getText();
		String password = editPasswordField.getText();
		StudentController controller = new StudentController(this.parentView);
		controller.updateStudent(studentId, name, gender, dob, phone, address, password);
		updateStudentTable();
	}
}