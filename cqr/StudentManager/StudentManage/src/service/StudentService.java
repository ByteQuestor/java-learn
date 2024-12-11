package service;

import dao.StudentDao;
import entity.Student;

import java.util.List;

public class StudentService {
    private StudentDao StudentDao = new StudentDao();

    public List<Student> getAllStudents() {
        return StudentDao.getAllStudents();
    }

    public void addStudent(Student student) {
        StudentDao.addStudent(student);
    }

    public void updateStudent(Student student) {
        StudentDao.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        StudentDao.deleteStudent(studentId);
    }
}
