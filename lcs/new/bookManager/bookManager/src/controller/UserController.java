package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.DatabaseManager;
import view.Choose;
import view.select;

public class UserController {
	// 添加用户到到数据库
	public boolean addUser(String userId, String userName, String password, String user_role, String create_time) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseManager.getConnection();
			String insertQuery = "INSERT INTO users (user_id, username, password, user_role, create_time) VALUES ( ?, ?, ?, ?,?)";
			preparedStatement = connection.prepareStatement(insertQuery);

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userName);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, user_role);
			preparedStatement.setString(5, create_time);
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				System.out.println("用户信息添加进去了！");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, null);
		}
		return false;
	}

	public int checkLogin(String username, String password, String userRole) {
		try {
			// 建立数据库连接（根据实际情况修改URL、用户名、密码）
			Connection connection = DatabaseManager.getConnection();

			// 准备SQL查询语句，防止SQL注入
			String sql = "SELECT * FROM users WHERE username =? AND password =? AND user_role =?";
			// Connection connection;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, userRole);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				JOptionPane.showMessageDialog(null, "登录成功！");
				// 关闭资源
				resultSet.close();
				preparedStatement.close();
				connection.close();
				return 1;
			} else {
				JOptionPane.showMessageDialog(null, "用户名、密码或用户身份错误，请重新输入！");
				// 关闭资源
				resultSet.close();
				preparedStatement.close();
				connection.close();
				return 2;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库连接或查询出错，请检查配置！");
		}
		
		return -1;
	}
}
