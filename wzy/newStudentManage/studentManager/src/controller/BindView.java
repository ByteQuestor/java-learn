package controller;

import view.StudentManagerView;

public class BindView {
	public static void bind(int role, String name, String password) {
		// 创建视图
		StudentManagerView view = new StudentManagerView(role, name, password);
		if (role == 0) {
			System.out.print("进入学生视角");
			CourseController courseController = new CourseController(view);
			courseController.loadCourseData();
		} else if (role == 1) {
			System.out.print("进入老师视角");
			// 创建控制器，并关联视图
			StudentController teacherController = new StudentController(view);
			// 加载学生数据并显示
			teacherController.loadStudentData();
		} else if (role == 2) {
			System.out.print("进入管理员视角");
			// 创建控制器，并关联视图
			UserController userController = new UserController(view);
			// 加载用户数据并显示
			userController.loadUserData();
		}
		
		// 显示界面
		view.getFrame().setVisible(true);
	}
}
