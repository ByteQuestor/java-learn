package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// 导航面板类，创建包含导航按钮的面板，用于切换不同功能界面
public class NavigationPanel {

	private JPanel panel;
	private List<JButton> buttons;

	public NavigationPanel(int role) {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(150, 600));

		buttons = new ArrayList<>();
//        JButton btnQuery = createNavButton("工作桌面", "query");
//        JButton btnAdd = createNavButton("新增信息", "add");
////        JButton btnUpdate = createNavButton("课表信息", "update");
////        JButton btnDelete = createNavButton("操作记录", "delete");
//
//        buttons.add(btnQuery);
//        buttons.add(btnAdd);
////        buttons.add(btnUpdate);
////        buttons.add(btnDelete);
		// 根据角色添加不同的导航按钮
		addCommonButtons(); // 所有角色都有的按钮

		if (role == 0) { // 学生
			// 学生只需要工作桌面
			addStudentButtons();
		} else if (role == 1) { // 老师
			// 老师有工作桌面和新增信息
			addTeacherButtons();
		} else if (role == 2) { // 管理员
			// 管理员有所有按钮
			addAdminButtons();
		}
		for (JButton button : buttons) {
			panel.add(button);
		}
	}

	private JButton createNavButton(String text, String cardName) {
		JButton button = new JButton(text);
		button.setActionCommand(cardName);
		return button;
	}

	public JPanel getPanel() {
		return panel;
	}

	public List<JButton> getButtons() {
		return buttons;
	}
    // 所有角色共享的按钮
    private void addCommonButtons() {
        JButton btnQuery = createNavButton("工作桌面", "query");
        buttons.add(btnQuery);
    }
    // 学生角色的按钮
    private void addStudentButtons() {
        // 学生不需要额外的按钮，只需要“工作桌面”
    }
	// 老师角色的按钮
	private void addTeacherButtons() {
		JButton btnAdd = createNavButton("新增信息", "add");
		buttons.add(btnAdd);
	}

	// 管理员角色的按钮
	private void addAdminButtons() {
		JButton btnAdd = createNavButton("新增信息", "add");
		JButton btnUpdate = createNavButton("课表信息", "update");
		JButton btnDelete = createNavButton("操作记录", "delete");

		buttons.add(btnAdd);
		buttons.add(btnUpdate);
		buttons.add(btnDelete);
	}
}