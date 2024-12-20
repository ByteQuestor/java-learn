package school;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("课程已添加。");
    }

    public void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("没有课程信息。");
            return;
        }
        System.out.println("\n所有课程信息：");
        for (Course course : courses) {
            System.out.println("课程编号: " + course.courseId + ", 课程名称: " + course.courseName);
        }
    }

    public void deleteCourse(String courseId) {
        boolean removed = courses.removeIf(course -> course.courseId.equals(courseId));
        if (removed) {
            System.out.println("课程已删除。");
        } else {
            System.out.println("未找到课程。");
        }
    }

    public void updateCourse(String courseId, String newName) {
        for (Course course : courses) {
            if (course.courseId.equals(courseId)) {
                course.courseName = newName;
                System.out.println("课程信息已更新。");
                return;
            }
        }
        System.out.println("未找到课程。");
    }

    public void exportCourses() {
        System.out.println("导出课程信息...");
        for (Course course : courses) {
            System.out.println("课程编号: " + course.courseId + ", 课程名称: " + course.courseName);
        }
    }
}
