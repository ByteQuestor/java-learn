package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// 导航面板类，创建包含导航按钮的面板，用于切换不同功能界面
public class NavigationPanel {

    private JPanel panel;
    private List<JButton> buttons;

    public NavigationPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 600));

        buttons = new ArrayList<>();
        JButton btnQuery = createNavButton("工作桌面", "query");
        JButton btnAdd = createNavButton("新增信息", "add");
        JButton btnUpdate = createNavButton("课表信息", "update");
        JButton btnDelete = createNavButton("操作记录", "delete");

        buttons.add(btnQuery);
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

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
}