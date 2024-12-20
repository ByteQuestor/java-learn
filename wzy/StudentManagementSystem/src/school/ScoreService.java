package school;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    private final List<Score> scores = new ArrayList<>();

    public void addScore(Score score) {
        scores.add(score);
        System.out.println("成绩已录入。");
    }

    public void viewAllScores() {
        if (scores.isEmpty()) {
            System.out.println("没有成绩信息。");
            return;
        }
        System.out.println("\n所有成绩信息：");
        for (Score score : scores) {
            System.out.println("学号: " + score.studentId + ", 课程编号: " + score.courseId + ", 成绩: " + score.scoreValue);
        }
    }

    public void deleteScore(String studentId, String courseId) {
        boolean removed = scores.removeIf(score -> score.studentId.equals(studentId) && score.courseId.equals(courseId));
        if (removed) {
            System.out.println("成绩记录已删除。");
        } else {
            System.out.println("未找到成绩记录。");
        }
    }

    public void exportScores() {
        System.out.println("导出成绩信息...");
        for (Score score : scores) {
            System.out.println("学号: " + score.studentId + ", 课程编号: " + score.courseId + ", 成绩: " + score.scoreValue);
        }
    }
}
