package school;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("学生已添加。");
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("没有学生信息。");
            return;
        }
        System.out.println("\n所有学生信息：");
        for (Student student : students) {
            System.out.println("学号: " + student.id + ", 姓名: " + student.name);
        }
    }

    public void deleteStudent(String id) {
        boolean removed = students.removeIf(student -> student.id.equals(id));
        if (removed) {
            System.out.println("学生已删除。");
        } else {
            System.out.println("未找到学生。");
        }
    }

    public void updateStudent(String id, String newName) {
        for (Student student : students) {
            if (student.id.equals(id)) {
                student.name = newName;
                System.out.println("学生信息已更新。");
                return;
            }
        }
        System.out.println("未找到学生。");
    }

    public void exportStudents() {
        System.out.println("导出学生信息...");
        for (Student student : students) {
            System.out.println("学号: " + student.id + ", 姓名: " + student.name);
        }
    }
}
