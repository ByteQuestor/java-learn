

package view;

import javax.swing.*;

import controller.StudentController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// 主视图类，负责整合各个子组件和模块，构建完整的学生信息管理系统界面
public class StudentManagerView {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    //封装类
    private NavigationPanel navigationPanel;
    private QueryPanel queryPanel;
    private AddPanel addPanel;
    private UpdatePanel updatePanel;
    //private DeletePanel deletePanel;

    public StudentManagerView(int role) {
        initialize(role);
    }

    private void initialize(int role) {
        frame = new JFrame("学生信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // 初始化各个功能面板
        navigationPanel = new NavigationPanel(role);
        queryPanel = new QueryPanel(this);
        addPanel = new AddPanel(this);
        updatePanel = new UpdatePanel();
        //deletePanel = new DeletePanel();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
//        cardPanel.add(queryPanel.getPanel(), "query");
//        cardPanel.add(addPanel.getPanel(), "add");
        //cardPanel.add(updatePanel.getPanel(), "update");
        //cardPanel.add(deletePanel.getPanel(), "delete");

        frame.getContentPane().add(navigationPanel.getPanel(), BorderLayout.WEST);
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // 状态栏
        if(role == 0) {
        	JLabel statusLabel = new JLabel("当前身份：学生", JLabel.CENTER);
            statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        }else if(role==1) {
        	System.out.print("老师");
            cardPanel.add(queryPanel.getPanel(), "query");
            cardPanel.add(addPanel.getPanel(), "add");
            //cardPanel.add(updatePanel.getPanel(), "update");
            //cardPanel.add(deletePanel.getPanel(), "delete");
        	JLabel statusLabel = new JLabel("当前身份：老师", JLabel.CENTER);
            statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        }else if(role==2) {
            cardPanel.add(queryPanel.getPanel(), "query");
            cardPanel.add(addPanel.getPanel(), "add");
            cardPanel.add(updatePanel.getPanel(), "update");
            //cardPanel.add(deletePanel.getPanel(), "delete");
        	JLabel statusLabel = new JLabel("当前身份：管理员", JLabel.CENTER);
            statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        }
        

        // 处理导航按钮点击事件，关联到对应面板的显示切换
        handleNavigationButtonClicks();
    }

    private void handleNavigationButtonClicks() {
        for (JButton button : navigationPanel.getButtons()) {
            String cardName = button.getActionCommand();
            button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void addSubmitListener(ActionListener listener) {
        queryPanel.addSubmitListener(listener);
    }

    public void updateStudentTable() {
        queryPanel.updateStudentTable();
    }

    public void displayStudents(List<String[]> students) {
        queryPanel.displayStudents(students);
    }

}