# 设计数据库

### 1. **学生表 (students)**

| 列名         | 数据类型       | 约束                            | 说明               |
| ------------ | -------------- | ------------------------------- | ------------------ |
| `student_id` | `INT`          | `AUTO_INCREMENT`, `PRIMARY KEY` | 学生ID，主键，自增 |
| `name`       | `VARCHAR(100)` | `NOT NULL`                      | 学生姓名           |
| `gender`     | `VARCHAR(10)`  |                                 | 性别               |
| `birth_date` | `DATE`         |                                 | 出生日期           |
| `phone`      | `VARCHAR(15)`  |                                 | 电话               |
| `address`    | `VARCHAR(255)` |                                 | 地址               |

### 2. **课程表 (courses)**

| 列名           | 数据类型       | 约束                            | 说明               |
| -------------- | -------------- | ------------------------------- | ------------------ |
| `course_id`    | `INT`          | `AUTO_INCREMENT`, `PRIMARY KEY` | 课程ID，主键，自增 |
| `course_name`  | `VARCHAR(100)` | `NOT NULL`                      | 课程名称           |
| `teacher_name` | `VARCHAR(100)` |                                 | 教师姓名           |

### 3. **成绩表 (grades)**

| 列名         | 数据类型        | 约束                            | 说明                 |
| ------------ | --------------- | ------------------------------- | -------------------- |
| `grade_id`   | `INT`           | `AUTO_INCREMENT`, `PRIMARY KEY` | 成绩ID，主键，自增   |
| `student_id` | `INT`           |                                 | 学生ID（无外键约束） |
| `course_id`  | `INT`           |                                 | 课程ID（无外键约束） |
| `grade`      | `DECIMAL(5, 2)` |                                 | 成绩                 |

数据库在`sql/studentmanage.sql`，直接运行即可恢复

# 项目结构

本次采用`MVC`设计模式

```tex
StudentManagementSystem
│
├── src
│   ├── entity           # 存放实体类
│   │   └── Student.java         # 学生实体类
│   ├── dao              # 数据访问层，负责与数据库交互
│   │   └── StudentDao.java     # 学生数据访问对象，提供操作数据库的方法
│   ├── service          # 业务逻辑层
│   │   └── StudentService.java   # 增删改查
│   ├── controller       # 控制层，负责从视图层接受输入并调用业务逻辑
│   │   └── StudentController.java # 学生控制器
│   ├── view             # 视图层，负责与用户进行交互
│   │   └── StudentFrame.java    # 学生视图
│   └── util             # 工具类，提供一些公用方法
│       └── DBUtil.java         # 数据库连接工具类
│   └── main             # 主程序
│       └── main.java         # 主程序
│
└── lib                  # 存放外部依赖库（如 JDBC 驱动）
│
└── sql                  # 存放数据库文件
│
```

# 测试数据库连接

## 配置驱动

右击项目

`Build Path→Configure Build Path→Libraries→Add External JARs`选择项目目录下的`lib/mysql-connector-j-9.0.0.jar`

# 编写代码

## 工具类`util`

```java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/studentmanage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private static final String USER = "root"; // 数据库用户名
	private static final String PASSWORD = "000000"; // 数据库密码

	// 获取数据库连接
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//返回数据库连接
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("数据库驱动加载失败", e);
		}
	}

	// 测试连接
	public static void main(String[] args) {
		try (Connection connection = DBUtil.getConnection()) {
			if (connection != null) {
				System.out.println("数据库连接成功！");
			}
		} catch (SQLException e) {
			System.out.println("数据库连接失败: " + e.getMessage());
		}
	}
}

```

控制台显示：**数据库连接成功！**说明成功

## 逻辑层`service`

```java
package service;

import dao.StudentDao;
import entity.Student;

import java.util.List;

public class StudentService {
    private StudentDao StudentDao = new StudentDao();

    public List<Student> getAllStudents() {
        return StudentDao.getAllStudents();
    }

    public void addStudent(Student student) {
        StudentDao.addStudent(student);
    }

    public void updateStudent(Student student) {
        StudentDao.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        StudentDao.deleteStudent(studentId);
    }
}
```

## 控制层`controller`

```java
package controller;

import service.StudentService;
import entity.Student;

import java.util.List;

public class StudentController {
    private StudentService studentService = new StudentService();

    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    public void addStudent(Student student) {
        studentService.addStudent(student);
    }

    public void updateStudent(Student student) {
        studentService.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        studentService.deleteStudent(studentId);
    }
}
```

## 实体类`entity`

```java
package entity;

public class Student {
    private int studentId;      // 学生ID
    private String name;        // 姓名
    private String gender;      // 性别
    private String birthDate;   // 出生日期
    private String phone;       // 电话
    private String address;     // 地址

    // 构造函数
    public Student(int studentId, String name, String gender, String birthDate, String phone, String address) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```

## 数据访问层

```java
package dao;

import entity.Student;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    // 查询所有学生
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("birth_date"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // 插入学生
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, gender, birth_date, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setString(3, student.getBirthDate());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getAddress());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新学生
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, gender = ?, birth_date = ?, phone = ?, address = ? WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setString(3, student.getBirthDate());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getAddress());
            stmt.setInt(6, student.getStudentId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除学生
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

```

## 视图层`view`

```java
package view;

import controller.StudentController;
import entity.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentFrame extends JFrame {
    private StudentController studentController = new StudentController();
    private JTable studentTable;  // 学生信息展示表格
    private DefaultTableModel tableModel;

    public StudentFrame() {
        setTitle("学生管理系统");
        setSize(600, 400);
        setLocationRelativeTo(null);  // 居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建表格
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"ID", "姓名", "性别", "出生日期", "电话", "地址"});
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("添加学生");
        JButton btnUpdate = new JButton("更新学生");
        JButton btnDelete = new JButton("删除学生");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // 添加组件到窗口
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 加载学生数据
        loadStudentData();

        // 添加按钮事件
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddStudentDialog();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateStudentDialog();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    // 加载所有学生数据到表格
    private void loadStudentData() {
        List<Student> students = studentController.getAllStudents();
        tableModel.setRowCount(0);  // 清空表格
        for (Student student : students) {
            tableModel.addRow(new Object[] {
                student.getStudentId(), student.getName(), student.getGender(), student.getBirthDate(),
                student.getPhone(), student.getAddress()
            });
        }
    }

    // 弹出对话框添加学生
    private void showAddStudentDialog() {
        JTextField nameField = new JTextField(20);
        JTextField genderField = new JTextField(10);
        JTextField birthDateField = new JTextField(10);
        JTextField phoneField = new JTextField(15);
        JTextField addressField = new JTextField(30);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("性别:"));
        panel.add(genderField);
        panel.add(new JLabel("出生日期 (YYYY-MM-DD):"));
        panel.add(birthDateField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);

        int option = JOptionPane.showConfirmDialog(this, panel, "添加学生", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String gender = genderField.getText();
            String birthDate = birthDateField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            Student student = new Student(0, name, gender, birthDate, phone, address);
            studentController.addStudent(student);
            loadStudentData();
        }
    }

    // 弹出对话框更新学生信息
    private void showUpdateStudentDialog() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要更新的学生", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) studentTable.getValueAt(selectedRow, 0);
        String name = (String) studentTable.getValueAt(selectedRow, 1);
        String gender = (String) studentTable.getValueAt(selectedRow, 2);
        String birthDate = (String) studentTable.getValueAt(selectedRow, 3);
        String phone = (String) studentTable.getValueAt(selectedRow, 4);
        String address = (String) studentTable.getValueAt(selectedRow, 5);

        JTextField nameField = new JTextField(name, 20);
        JTextField genderField = new JTextField(gender, 10);
        JTextField birthDateField = new JTextField(birthDate, 10);
        JTextField phoneField = new JTextField(phone, 15);
        JTextField addressField = new JTextField(address, 30);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("性别:"));
        panel.add(genderField);
        panel.add(new JLabel("出生日期 (YYYY-MM-DD):"));
        panel.add(birthDateField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);

        int option = JOptionPane.showConfirmDialog(this, panel, "更新学生", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Student student = new Student(studentId, nameField.getText(), genderField.getText(),
                    birthDateField.getText(), phoneField.getText(), addressField.getText());
            studentController.updateStudent(student);
            loadStudentData();
        }
    }

    // 删除学生
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的学生", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) studentTable.getValueAt(selectedRow, 0);
        studentController.deleteStudent(studentId);
        loadStudentData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentFrame().setVisible(true);
            }
        });
    }
}

```

# 程序入口`main`

```java
package main;

import view.StudentFrame;

public class main {
    public static void main(String[] args) {
        // 启动学生管理系统图形界面
        StudentFrame studentFrame = new StudentFrame();
        studentFrame.setVisible(true);  // 显示学生管理窗口
    }
}
```

