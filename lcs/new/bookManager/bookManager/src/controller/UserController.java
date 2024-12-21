package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.CurrentTime;
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
			//获取时间
			CurrentTime getCurrentTime = new CurrentTime();
			String currentTime = getCurrentTime.CurrentTime();
			preparedStatement.setString(5, currentTime);
			
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
			// 封装了数据库链接
			Connection connection = DatabaseManager.getConnection();

			// 准备SQL查询语句，防止SQL注入
			String sql = "SELECT * FROM users WHERE username =? AND password =? AND user_role =?";
			// Connection connection;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, userRole);
			//获取时间
			CurrentTime getCurrentTime = new CurrentTime();
			String currentTime = getCurrentTime.CurrentTime();
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				JOptionPane.showMessageDialog(null, "登录成功！");
				
				//插入最后登录时间
				String updateSql = "UPDATE users SET last_login =? WHERE username =? AND user_role =?";
				
				PreparedStatement updatePreparedStatement = connection.prepareStatement(updateSql);
                updatePreparedStatement.setString(1, currentTime);
                updatePreparedStatement.setString(2, username);
                updatePreparedStatement.setString(3, userRole);

                // 执行更新操作，插入登录时间
                int rowsAffected = updatePreparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("登录时间插入成功");
                } else {
                    System.out.println("登录时间插入失败");
                }
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
