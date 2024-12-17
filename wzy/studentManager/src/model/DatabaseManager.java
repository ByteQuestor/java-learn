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
