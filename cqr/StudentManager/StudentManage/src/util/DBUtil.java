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
