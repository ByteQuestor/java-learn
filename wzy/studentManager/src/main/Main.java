package main;

import controller.StudentController;
import view.StudentManagerView;

public class Main {
    public static void main(String[] args) {
        // 创建视图
        StudentManagerView view = new StudentManagerView();

        // 创建控制器，并关联视图
        StudentController controller = new StudentController(view);

        // 加载学生数据并显示
        controller.loadStudentData();

        // 显示界面
        view.getFrame().setVisible(true);
    }
}
