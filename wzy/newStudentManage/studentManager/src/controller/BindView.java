package controller;

import view.StudentManagerView;

public class BindView {
	public static void bind(int role) {
		// 创建视图
		StudentManagerView view = new StudentManagerView(role);
		if (role == 0) {
			System.out.print("进入学生视角");
//			CourseController courseController = new CourseController(view);
//			courseController.loadCourseData();
			// 创建控制器，并关联视图
			StudentController teacherController = new StudentController(view);
			// 加载学生数据并显示
			teacherController.loadStudentData();
		} else if (role == 1) {
			System.out.print("进入老师视角");
			// 创建控制器，并关联视图
			StudentController teacherController = new StudentController(view);
			// 加载学生数据并显示
			teacherController.loadStudentData();
		} else if (role == 2) {
			System.out.print("进入管理员视角");
			// 创建控制器，并关联视图
			StudentController teacherController = new StudentController(view);
			// 加载学生数据并显示
			teacherController.loadStudentData();
		}

		// 显示界面
		view.getFrame().setVisible(true);
	}
}
