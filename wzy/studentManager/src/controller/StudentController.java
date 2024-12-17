package controller;

import model.DatabaseManager;
import view.StudentManagerView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private StudentManagerView view;

    public StudentController(StudentManagerView view) {
        this.view = view;
    }

    // 获取学生信息并传递到视图层
    public void loadStudentData() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> students = new ArrayList<>();

        try {
            // 获取数据库连接
            connection = DatabaseManager.getConnection();
            String query = "SELECT * FROM students";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            // 获取学生数据
            while (resultSet.next()) {
                String studentId = String.valueOf(resultSet.getInt("student_id"));
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String birthDate = resultSet.getDate("birth_date").toString();
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                // 将数据存入 List 中
                students.add(new String[] { studentId, name, gender, birthDate, phone, address });
            }

            // 将数据传递到视图层渲染
            view.displayStudents(students);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, statement, resultSet);
        }
    }
}
