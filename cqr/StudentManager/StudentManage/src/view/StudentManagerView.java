package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import controller.StudentController;
import java.util.List;

public class StudentManagerView {

    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField studentNameField;
    private JTextField oldStudentNameField;
    private JTextField newStudentNameField;

    public StudentManagerView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("NEW 学生信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setPreferredSize(new Dimension(150, 600));

        JButton btnQuery = new JButton("信息查询");
        JButton btnAdd = new JButton("新增信息");
        JButton btnUpdate = new JButton("修改信息");
        JButton btnDelete = new JButton("删除信息");

        navigationPanel.add(btnQuery);
        navigationPanel.add(btnAdd);
        navigationPanel.add(btnUpdate);
        navigationPanel.add(btnDelete);

        frame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        PanelFactory panelFactory = new PanelFactory();
        JPanel queryPanel = panelFactory.createPanel("信息查询");
        JPanel addPanel = panelFactory.createPanel("新增信息");
        JPanel updatePanel = panelFactory.createPanel("修改信息");
        JPanel deletePanel = panelFactory.createPanel("删除信息");

        cardPanel.add(queryPanel, "query");
        cardPanel.add(addPanel, "add");
        cardPanel.add(updatePanel, "update");
        cardPanel.add(deletePanel, "delete");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("状态栏：系统正常运行", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);

        btnQuery.addActionListener(e -> cardLayout.show(cardPanel, "query"));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, "add"));
        btnUpdate.addActionListener(e -> cardLayout.show(cardPanel, "update"));
        btnDelete.addActionListener(e -> cardLayout.show(cardPanel, "delete"));
    }

    public void displayStudents(List<String> students) {
        JOptionPane.showMessageDialog(frame, "学生信息：" + String.join(", ", students));
    }

    public String getStudentNameInput() {
        return studentNameField.getText();
    }

    public String getOldStudentNameInput() {
        return oldStudentNameField.getText();
    }

    public String getNewStudentNameInput() {
        return newStudentNameField.getText();
    }

    public void addQueryListener(ActionListener listener) {
        // Add query listener
    }

    public void addAddListener(ActionListener listener) {
        // Add add listener
    }

    public void addUpdateListener(ActionListener listener) {
        // Add update listener
    }

    public void addDeleteListener(ActionListener listener) {
        // Add delete listener
    }

    public JFrame getFrame() {
        return frame;
    }
}
