package school;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import model.DatabaseManager; //数据库链接操作在这里，这个类可以保存下来复用

public class StudentManagementSystem {

	private static final Scanner scanner = new Scanner(System.in);
	private static final StudentService studentService = new StudentService();
	private static final CourseService courseService = new CourseService();
	private static final ScoreService scoreService = new ScoreService();
	private static final String CONFIG_FILE = "database.properties";
	//

	public static void main(String[] args) {
//        try (Connection connection = getConnection()) {
//            System.out.println("连接数据库成功！");
//            // 初始化数据库
//            SQLFileExecutor.executeSQLFile(connection, "F:/eclipse/StudentManagementSystem/src/resources/student_management.sql");
//            System.out.println("数据库初始化完成！");
//        } catch (Exception e) {
//            System.err.println("数据库连接失败：" + e.getMessage());
//            return;
//        }

		// 数据库链接，封装起来了
		try {
			Connection con = DatabaseManager.getConnection();
			/*
			 * 
			 * 在这里，搞一个查看所有的例子
			 * 就是这么个流程，我是死记硬背的，具体原理我不知道
			 */
			//构造一个sql语句，就是可以在命令行执行的语句
			String query = "SELECT * FROM studend";
			//创建一个 Statement 对象，用于向数据库发送sql语句获取结果。
			Statement statement = con.createStatement();
			//使用前面创建的Statement对象来执行具体的查询操作，获取查询结果集
			ResultSet resultSet = statement.executeQuery(query);
			System.out.print(resultSet);
			System.out.print("\n-------------------------\n");
			System.out.print(resultSet.next());
			System.out.print("\n-------------------------\n");
			while (resultSet.next()) {	//这个我就理解为下标，当结果集的下标不为空（也就是还没结束的时候，获取字段的值，resultSet是一条一条的
				//姓名 				ID 			性别 			院系			绩点
				//chenxiaolu 	2147483647 		n 		yixiangkan 		4
				String stuName = resultSet.getString("姓名");		//获取字段“姓名的字段”
				String stuId = String.valueOf(resultSet.getInt("ID"));
				String gender = resultSet.getString("性别");
				String yx = resultSet.getString("院系");
				String garden = String.valueOf(resultSet.getInt("绩点"));

				System.out.println(
						"姓名: " + stuName + ", ID: " + stuId + ", 性别: " + gender + ", 院系: " + yx + ", 绩点: " + garden);
				System.out.print(resultSet.next());//当下标到最后了，也就结束了，也就退出这个循环了
				System.out.print("\n-------------------------\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 主菜单逻辑
		while (true) {
			try {
				displayMainMenu();
				int menuChoice = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				if (menuChoice < 1 || menuChoice > 4) {
					System.out.println("无效选择，请输入1 - 4之间的数字。");
					continue;
				}
				switch (menuChoice) {
				case 1 -> manageStudents();
				case 2 -> manageCourses();
				case 3 -> manageScores();
				case 4 -> {
					System.out.println("系统退出，再见！");
					return;
				}
				}
			} catch (Exception e) {
				System.out.println("输入无效，请输入正确的选项！");
				scanner.nextLine(); // Clear invalid input
			}
		}
	}

	private static Connection getConnection() throws SQLException, IOException {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
			properties.load(fis);
		}
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		return DriverManager.getConnection(url, username, password);
	}

	private static void displayMainMenu() {
		System.out.println("\n欢迎使用学生成绩管理系统");
		System.out.println("1. 学生信息管理");
		System.out.println("2. 课程信息管理");
		System.out.println("3. 成绩管理");
		System.out.println("4. 退出系统");
		System.out.print("请选择操作：");
	}

	private static void manageStudents() {
		Runnable[] studentOperations = { StudentManagementSystem::addStudent, studentService::viewAllStudents,
				StudentManagementSystem::deleteStudent, StudentManagementSystem::updateStudent, () -> {
				} };
		manageEntity(studentOperations);
	}

	private static void manageCourses() {
		Runnable[] courseOperations = { StudentManagementSystem::addCourse, courseService::viewAllCourses,
				StudentManagementSystem::deleteCourse, StudentManagementSystem::updateCourse, () -> {
				} };
		manageEntity(courseOperations);
	}

	private static void manageScores() {
		// Implement similar to students and courses
		// 这里可以按照学生和课程管理的方式实现成绩管理的具体逻辑
		// 例如定义Runnable数组，调用manageEntity方法
	}

	private static void manageEntity(Runnable[] operations) {
		while (true) {
			try {
				displayGenericMenu(operations.length);
				int menuChoice = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				if (menuChoice < 1 || menuChoice > operations.length) {
					System.out.println("无效选择，请输入1 - " + operations.length + "之间的数字。");
					continue;
				}
				operations[menuChoice - 1].run();
				if (operations[menuChoice - 1] == operations[operations.length - 1]) {
					return;
				}
			} catch (Exception e) {
				System.out.println("输入无效，请输入正确的选项！");
				scanner.nextLine(); // Clear invalid input
			}
		}
	}

	private static void displayGenericMenu(int menuSize) {
		System.out.println("\n请选择操作：");
		for (int i = 0; i < menuSize; i++) {
			System.out.println((i + 1) + ". " + getOperationName(i));
		}
	}

	private static String getOperationName(int index) {
		if (index == 0)
			return "添加";
		if (index == 1)
			return "查看所有";

		if (index == 3)
			return "更新";
		if (index == 2)
			return "删除";
		return "";
	}

	private static void addStudent() {
		System.out.print("请输入学生学号：");
		String id = scanner.nextLine();
		System.out.print("请输入学生姓名：");
		String name = scanner.nextLine();
		studentService.addStudent(new Student(id, name));
	}

	private static void deleteStudent() {
		System.out.print("请输入要删除的学生学号：");
		String id = scanner.nextLine();
		studentService.deleteStudent(id);
	}

	private static void updateStudent() {
		System.out.print("请输入要更新的学生学号：");
		String id = scanner.nextLine();
		System.out.print("请输入新的姓名：");
		String name = scanner.nextLine();
		studentService.updateStudent(id, name);
	}

	private static void addCourse() {
		System.out.print("请输入课程编号：" + '\n');
		String id = scanner.nextLine();
		System.out.print("请输入课程名称：" + '\n');
		String name = scanner.nextLine();
		courseService.addCourse(new Course(id, name));
	}

	private static void deleteCourse() {
		System.out.print("请输入要删除的课程编号：");
		String id = scanner.nextLine();
		courseService.deleteCourse(id);
	}

	private static void updateCourse() {
		System.out.print("请输入要更新的课程编号：");
		String id = scanner.nextLine();
		System.out.print("请输入新的课程名称：");
		String name = scanner.nextLine();
		courseService.updateCourse(id, name);
	}

	@SuppressWarnings("unused")
	private static void exportData() {
		studentService.exportStudents();
		courseService.exportCourses();
		scoreService.exportScores();
		System.out.println("数据已成功导出！");
	}
}