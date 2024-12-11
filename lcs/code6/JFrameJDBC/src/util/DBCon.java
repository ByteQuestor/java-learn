package util;

import java.sql.*;

public class DBCon {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbcudp";
    private static final String USER = "root";
    private static final String PASSWORD = "000000";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
