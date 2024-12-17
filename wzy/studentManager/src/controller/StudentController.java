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

    // Get student data and pass to the view
    public void loadStudentData() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String[]> students = new ArrayList<>();

        try {
            connection = DatabaseManager.getConnection();
            String query = "SELECT * FROM students";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String studentId = String.valueOf(resultSet.getInt("student_id"));
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String birthDate = resultSet.getDate("birth_date").toString();
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                students.add(new String[] { studentId, name, gender, birthDate, phone, address });
            }

            view.displayStudents(students);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, statement, resultSet);
        }
    }

    // Add student to the database
    public void addStudent(String studentId, String name, String gender, String birthDate, String phone, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String insertQuery = "INSERT INTO students (student_id, name, gender, birth_date, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, birthDate);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, address);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息添加成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }

    // Update student info
    public void updateStudent(String studentId, String name, String gender, String birthDate, String phone, String address) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String updateQuery = "UPDATE students SET name = ?, gender = ?, birth_date = ?, phone = ?, address = ? WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, birthDate);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息修改成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }

    // Delete student info
    public void deleteStudent(String studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseManager.getConnection();
            String deleteQuery = "DELETE FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("学生信息删除成功");
            } else {
                System.out.println("未找到该学生，删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection(connection, preparedStatement, null);
        }
    }
}
