package controller;

import model.DatabaseManager;
import model.User;

import java.sql.*;

public class UserController {

	// 登录验证方法
    public boolean login(String username, String password,String role) {
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
            String query = "SELECT * FROM User WHERE name = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            //preparedStatement.setString(3, role);
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
}
