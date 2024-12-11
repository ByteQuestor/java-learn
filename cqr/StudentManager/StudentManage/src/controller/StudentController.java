package controller;

import service.StudentService;
import entity.Student;

import java.util.List;

public class StudentController {
    private StudentService studentService = new StudentService();

    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    public void addStudent(Student student) {
        studentService.addStudent(student);
    }

    public void updateStudent(Student student) {
        studentService.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        studentService.deleteStudent(studentId);
    }
}
