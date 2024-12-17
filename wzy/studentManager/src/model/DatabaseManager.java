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

    // 查询所有学生信息
    public static void getAllStudents() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // 获取数据库连接
            connection = getConnection();
            System.out.println("数据库连接成功！");

            // 执行查询语句，查询所有学生
            String query = "SELECT * FROM students";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            // 输出查询结果
            System.out.println("所有学生信息：");
            while (resultSet.next()) {
                System.out.println("学生ID: " + resultSet.getInt("student_id"));
                System.out.println("姓名: " + resultSet.getString("name"));
                System.out.println("性别: " + resultSet.getString("gender"));
                System.out.println("出生日期: " + resultSet.getDate("birth_date"));
                System.out.println("电话: " + resultSet.getString("phone"));
                System.out.println("地址: " + resultSet.getString("address"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            closeConnection(connection, statement, resultSet);
        }
    }

    // 主函数
    public static void main(String[] args) {
        getAllStudents();  // 查询并打印所有学生的信息
    }
}
