package controller;

import model.DatabaseManager;
import model.User;
import view.StudentManagerView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
	private StudentManagerView view;

	public UserController(StudentManagerView view) {
		this.view = view;
	}

	// 登录验证方法
	public boolean login(String username, String password, String role) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseManager.getConnection();
			String query = "SELECT * FROM User WHERE username = ? AND password = ? AND role = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, role);
			resultSet = preparedStatement.executeQuery();

			// 如果查询结果存在，表示验证通过
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	// 登录验证方法
	public boolean studentLogin(String username, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseManager.getConnection();
			String query = "SELECT * FROM students WHERE name = ? AND password = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			// preparedStatement.setString(3, role);
			resultSet = preparedStatement.executeQuery();

			// 如果查询结果存在，表示验证通过
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	// 根据用户名获取用户信息
	public User getUserByUsername(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseManager.getConnection();
			String query = "SELECT * FROM User WHERE username = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String password = resultSet.getString("password");
				int role = resultSet.getInt("role");
				return new User(id, name, username, password, role);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	// 添加学生到数据库
	public void addUser(String userId, String userName, String name, String role, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseManager.getConnection();
			String insertQuery = "INSERT INTO user (id, name, username,role, password) VALUES (?,?, ?, ?,?)";
			preparedStatement = connection.prepareStatement(insertQuery);

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userName);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, role);
			preparedStatement.setString(5, password);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("用户信息添加成功");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, null);
		}
	}

	// 加载数据库数据
	public void loadUserData() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<String[]> users = new ArrayList<>();

		try {
			connection = DatabaseManager.getConnection();
			String query = "SELECT * FROM user";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				String courseId = String.valueOf(resultSet.getInt("id"));
				String courseName = resultSet.getString("name");
				String courseUserName = resultSet.getString("username");
				String coursePassword = resultSet.getString("password");
				String courseRole = resultSet.getString("role");

				users.add(new String[] { courseId, courseName, courseUserName, coursePassword, courseRole });
			}

			view.displayUsers(users);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(connection, statement, resultSet);
		}
	}

	// 删除学生信息
	public void deleteUser(String userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseManager.getConnection();
			String deleteQuery = "DELETE FROM user WHERE id = ?";
			preparedStatement = connection.prepareStatement(deleteQuery);
			preparedStatement.setString(1, userId);

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("用户信息删除成功");
			} else {
				System.out.println("未找到该学生，删除失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseManager.closeConnection(connection, preparedStatement, null);
		}
	}
	
	// 更新学生信息
    public void updateUser(String userId, String name,String realname, String role, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String updateQuery = "UPDATE user SET name = ?, username = ?, password = ?, role = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, realname);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("用户信息修改成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }

}
