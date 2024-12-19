package pag;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import view.PanelFactory;

public class Initialize {
	private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTable allStudentTable;
    private JTextField editStudentIdField, editNameField, dobField, editPhoneField, editAddressField;
	Initialize(){
		//构造函数
		initialize();
	}
	private void initialize() {
        frame = new JFrame("学生信息管理系统");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // 导航面板，放在左边
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setPreferredSize(new Dimension(150, 600));
        JButton btnQuery = createNavButton("工作桌面", "query");
        JButton btnAdd = createNavButton("新增信息", "add");
        JButton btnUpdate = createNavButton("课表信息", "update");
        JButton btnDelete = createNavButton("操作记录", "delete");

        navigationPanel.add(btnQuery);
        navigationPanel.add(btnAdd);
        navigationPanel.add(btnUpdate);
        navigationPanel.add(btnDelete);
        frame.getContentPane().add(navigationPanel, BorderLayout.WEST);

        // 卡片布局，用于切换不同的面板
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        PanelFactory panelFactory = new PanelFactory();
        JPanel queryPanel = panelFactory.createPanel("欢迎界面");
        JPanel addPanel = panelFactory.createPanel("新增信息");
        JPanel updatePanel = panelFactory.createPanel("开发中");
        JPanel deletePanel = panelFactory.createPanel("操作记录");

        cardPanel.add(queryPanel, "query");
        cardPanel.add(addPanel, "add");
        cardPanel.add(updatePanel, "update");
        cardPanel.add(deletePanel, "delete");
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // 状态栏
        JLabel statusLabel = new JLabel("状态栏：系统正常运行", JLabel.CENTER);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);

        // 处理【导航按钮】点击
        btnQuery.addActionListener(e -> cardLayout.show(cardPanel, "query"));
        btnAdd.addActionListener(e -> cardLayout.show(cardPanel, "add"));
        btnUpdate.addActionListener(e -> cardLayout.show(cardPanel, "update"));
        btnDelete.addActionListener(e -> cardLayout.show(cardPanel, "delete"));

        // 查询面板：包含表格
        JPanel queryContentPanel = new JPanel(new BorderLayout());
        allStudentTable = new JTable();
        allStudentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 当用户点击表格行时，填充信息到修改表单
                int selectedRow = allStudentTable.getSelectedRow();
                if (selectedRow != -1) {
                    editStudentIdField.setText(allStudentTable.getValueAt(selectedRow, 0).toString());
                    editNameField.setText(allStudentTable.getValueAt(selectedRow, 1).toString());
                    genderComboBox.setSelectedItem(allStudentTable.getValueAt(selectedRow, 2).toString());
                    dobField.setText(allStudentTable.getValueAt(selectedRow, 3).toString());
                    editPhoneField.setText(allStudentTable.getValueAt(selectedRow, 4).toString());
                    editAddressField.setText(allStudentTable.getValueAt(selectedRow, 5).toString());
                    deleteRow = selectedRow;
                }
            }
        });
        queryContentPanel.add(new JScrollPane(allStudentTable), BorderLayout.CENTER);
        JPanel editPanel = createEditPanel();
        queryContentPanel.add(editPanel, BorderLayout.SOUTH);
        cardPanel.add(queryContentPanel, "query");

        // 新增学生面板
        JPanel addStudentPanel = new JPanel();
        addStudentForm(addStudentPanel);
        cardPanel.add(addStudentPanel, "add");

        // 【修改按钮】监听器
        submitButton.addActionListener(e -> handleUpdateButtonClick());

        // 【删除按钮】监听器
        deleteButton.addActionListener(e -> handleDeleteButtonClick());
    }
	// 创建导航按钮
    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        return button;
    }
}
