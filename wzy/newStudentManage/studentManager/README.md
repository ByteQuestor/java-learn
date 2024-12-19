# 学生管理系统

项目地址：`https://github.com/ByteQuestor/java-learn.git`

# 一、数据库设计

**数据库打包在`sql/students.sql`里，运行即可复原**

系统的主要功能是学生信息管理，包括查询、添加、更新和删除学生数据。这里我们设计一个简单的数据库结构，包括一个学生信息表（`students`）

**数据表：`students`**

这个表存储学生的基本信息，如学号、姓名、性别、年龄等。

```sql
CREATE DATABASE students;
USE students;
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `students` VALUES ('1', '蔡青茹', '女', '2000-01-01', '13900000001', '北京市海淀区');
INSERT INTO `students` VALUES ('2', 'wzy', '男', '2004-03-10', '13900000002', '上海市浦东新区');
INSERT INTO `students` VALUES ('3', '王五', '男', '2001-05-15', '13900000003', '广州市天河区');
INSERT INTO `students` VALUES ('4', '赵六', '女', '2000-07-20', '13900000004', '深圳市南山区');
INSERT INTO `students` VALUES ('5', '周七', '男', '1998-08-30', '13900000005', '重庆市渝中区');
```

**字段解释**

1. `student_id`: 学生的唯一标识符（主键），自增长。
2. `name`: 学生姓名。
3. `gender`: 学生的性别（使用枚举类型，可以扩展更多的性别选项）。
4. `age`: 学生的年龄。
5. `grade`: 学生的年级。
6. `major`: 学生的专业。
7. `created_at`: 学生信息的创建时间，默认为当前时间。
8. `updated_at`: 学生信息的最后更新时间，当更新时自动更新时间。

### 补充

根据题目实验要求：**用户角色划分明确，权限控制严格。**

还需要设计个**用户表**

# 二、设计模式

为 `MVC（Model-View-Controller）`模式，并进行模块化设计，我们可以将界面、逻辑处理、数据操作分开。`MVC` 模式将帮助实现代码的解耦，使得不同的功能模块可以更容易地更新和维护。

### 1. `MVC` 架构概述

- **Model（模型）**：负责数据的管理、处理和存储，独立于用户界面。
- **View（视图）**：负责显示用户界面，并响应用户的操作，由 `JFrame、JPanel、JButton` 等 `Swing` 组件构成。
- **Controller（控制器）**：负责处理用户输入并更新视图和模型。它接收来自视图的事件，调用模型中的方法，并将数据返回到视图。

### 2. 项目结构

- **controller**：负责与模型和视图的交互，处理用户操作的逻辑。
- **model**：管理数据（学生信息），包含对数据的增删查改操作，以及实体类。
- **view**：负责显示界面和用户交互。
  - `StudentManagerView`：负责整个界面的布局和展示。
  - `PanelFactory`：负责生成不同的功能面板，便于模块化管理。

```ini
src/
├── controller
│   └── StudentController.java
├── main
│   └── Main.java
├── model
│   ├── DatabaseManager.java
│   ├── Student.java
└── view
    ├── StudentManagerView.java
    └── PanelFactory.java
```

# 三、代码实现

# v1.0

### 1，数据库连接

#### `DatabaseManager.java`

> 数据库管理类 `DatabaseManager`，主要就包含两个方法：
>
> 1. **`getConnection()`**：用于建立与数据库的连接，使用 `MySQL JDBC` 驱动和指定的数据库 URL、用户名、密码。
> 2. **`closeConnection()`**：用于关闭数据库连接、声明和结果集，以释放资源并防止连接泄漏。
>
> `model`包下管理数据的角色

```java
package model;

import java.sql.*;

public class DatabaseManager {

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/students";
    private static final String USER = "root";       // 请替换为你的数据库用户名
    private static final String PASSWORD = "000000";  // 请替换为你的数据库密码

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("数据库连接失败！");
        }
    }

    // 关闭连接
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
		} catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

### 2，与数据库交互

#### `StudentController.java`

> `controller`类负责交互，例如所有学生信息都是由此类封装，然后new一个对象后通过对象调用
>
> 本次主要封装了四个方法（说他是行为可能更好理解一点）
>
> + `loadStudentData`加载数据库所有数据
> + `addStudent`添加学生
> + `updateStudent`更新信息
> + `deleteStudent`删除学生信息
> + `getMaxStudentId`获取当前数据库的数据条数（不过没用上）

```java
package controller;

import model.DatabaseManager;
import view.StudentManagerView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private StudentManagerView view;

    public StudentController(StudentManagerView view) {
        this.view = view;
    }

    // 加载数据库数据
    public void loadStudentData() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> students = new ArrayList<>();

        try {
            connection = DatabaseManager.getConnection();
            String query = "SELECT * FROM students";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String studentId = String.valueOf(resultSet.getInt("student_id"));
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String birthDate = resultSet.getDate("birth_date").toString();
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                students.add(new String[] { studentId, name, gender, birthDate, phone, address });
            }

            view.displayStudents(students);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, statement, resultSet);
        }
    }

    // 添加学生到数据库
    public void addStudent(String studentId, String name, String gender, String birthDate, String phone, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String insertQuery = "INSERT INTO students (student_id, name, gender, birth_date, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, birthDate);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, address);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息添加成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }

    // 更新学生信息
    public void updateStudent(String studentId, String name, String gender, String birthDate, String phone, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String updateQuery = "UPDATE students SET name = ?, gender = ?, birth_date = ?, phone = ?, address = ? WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, birthDate);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息修改成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }

    // 删除学生信息
    public void deleteStudent(String studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String deleteQuery = "DELETE FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息删除成功");
            } else {
                System.out.println("未找到该学生，删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }
    //获取当前最大的id值
    public int getMaxStudentId() {
        int maxStudentId = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 获取数据库连接
            connection = DatabaseManager.getConnection();
            
            // 查询最大学生ID
            String query = "SELECT MAX(student_id) FROM students";  // 根据你的表和字段名称进行调整
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            // 获取查询结果
            if (resultSet.next()) {
                maxStudentId = resultSet.getInt(1); // 获取最大学生ID
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DatabaseManager.closeConnection(connection, statement, resultSet);
        }
        return maxStudentId;  // 返回最大学生ID
    }
}
```

### 3，视图窗口

#### `StudentManagerView.java`

> 我们操作的主要`UI`窗口
>
> - **`MVC` 架构**：
> - **Model**：数据库操作（如增删改查）通常由 `StudentController` 和数据库相关类（如 `DatabaseManager`）实现。
> - **View**：` Swing` 构建用户界面，提供显示学生信息和表单输入的功能。
> - **Controller**：控制器类（`StudentController`）接收视图中的用户输入，调用模型中的方法处理数据，并更新视图。
>
> **Swing**：用于构建图形用户界面。代码通过 `Swing` 创建了一个具有多个界面（面板）切换功能的面板。
>
> 主要用到的组件有：
>
> - `JFrame`：主窗口。
> - `JPanel`、`BoxLayout`、`CardLayout`：容器面板和布局管理器，用于管理界面元素的布局和切换。
> - `JTable`：显示学生信息的表格，支持显示和管理学生数据。
> - `JButton`：点击事件（点点点用）
> - 、`JTextField`：输入框（渲染和输入用）
> - `JComboBox`：下拉选择器（用于点击选中某条记录时渲染性别和修改性别）
> - `JRadioButton`：单选按钮（用于新增信息是勾选性别）
> - `JSpinner` ：用于用户输入和交互。
> - `JScrollPane`：用于为表格提供滚动功能（这个平时看不出来，等添加太多数据的时候，就能出现效果）。
>
> **事件监听器**（ `ActionListener` 和 `MouseListener`）处理交互，就是按钮点击和表格行选择，来触发`CRUD`
>
> **数据绑定**：通过表格（`JTable`）来显示学生信息，并将数据与数据库中的记录关联。通过点击表格的某一行，可以自动填充到下方的编辑框中进行修改或删除操作。
>
> **日期处理**：使用 `JSpinner` 来选择日期，`SimpleDateFormat` 用于格式化日期。

```java
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
		editStudentIdField.setPreferredSize(new Dimension(2000000, 30)); // 设置大小
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
```

#### `PanelFactory.java`

> 根据传入的标题创建一个带有边框和标题的 `JPanel`

```java
package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelFactory {

    public JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Border panelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title);
        panel.setBorder(panelBorder);
        return panel;
    }
}
```

### 4，程序入口

#### Main.java

> 在此程序中，可以新建我们的窗口，**后续权限控制可以加在这里**

```java
package main;

import controller.StudentController;
import view.StudentManagerView;

public class Main {
    public static void main(String[] args) {
        // 创建视图
        StudentManagerView view = new StudentManagerView();

        // 创建控制器，并关联视图
        StudentController controller = new StudentController(view);

        // 加载学生数据并显示
        controller.loadStudentData();

        // 显示界面
        view.getFrame().setVisible(true);
    }
}

```

### 5，实体类

#### Student.java

> 这个单纯就是设计规范，要不然全定义到一个类里面很乱

```java
package model;

import java.sql.Timestamp;

public class Student {
    private int studentId;
    private String name;
    private String gender;
    private int age;
    private String grade;
    private String major;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 构造方法
    public Student(int studentId, String name, String gender, int age, String grade, String major, Timestamp createdAt, Timestamp updatedAt) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.grade = grade;
        this.major = major;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 和 Setter 方法

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
```

# v1.1

项目结构

```ini
|-- README.md     
|-- lib
|   `-- mysql-connector-j-9.0.0.jar
|-- sql
|   `-- students.sql
|-- src
|   |-- controller
|   |   |-- BindView.java			# 将视图绑定单独拉出来
|   |   `-- StudentController.java	# 学生类交互逻辑
|   |-- main
|   |   `-- Main.java				# 程序主入口
|   |-- model
|   |   |-- DatabaseManager.java	# 数据库链接类
|   |   `-- Student.java			# 学生类
|   `-- view
|       |-- AddPanel.java			# 新增信息窗口
|       |-- DeletePanel.java		# 删除信息窗口【未使用，功能已集合至QueryPanel，准备改造成课表】
|       |-- Login.java				# 登录窗口，权限控制【分为老师和学生】
|       |-- NavigationPanel.java	# 导航面板类，创建包含导航按钮的面板，用于切换不同功能界面
|       |-- QueryPanel.java			# 工作桌面窗口，会显示所有学生和修改、删除
|       |-- StudentManagerView.java	# 主界面窗口，包含上述小窗口
|       `-- UpdatePanel.java		# 更新信息窗口【未使用，功能已集合至QueryPanel，准备改造成消息记录】
`-- tree.exe
```

此版本概述：

+ 此版本解决了上个版本的冗余问题，将`StudentManagerView`类进行了拆分

  + `QueryPanel`
  + `NavigationPanel`
  + `AddPanel`
  + `BindView`

+ 新增权限控制

  + `Login`：主要分为学生登录和老师登录

## 程序概述

项目流程图：`https://www.processon.com/v/676420d971c12c3fe9f5b7e5?cid=67641fb7bdc47e0f5b40c78a`

  

