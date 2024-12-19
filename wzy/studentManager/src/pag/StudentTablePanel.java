package pag;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentTablePanel extends JPanel {

    private JTable allStudentTable;

    public StudentTablePanel() {
        setLayout(new BorderLayout());
        allStudentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(allStudentTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<String[]> students) {
        String[] columnNames = { "学生ID", "姓名", "性别", "出生日期", "电话", "地址" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        students.forEach(student -> model.addRow(student));
        allStudentTable.setModel(model);
    }

    public JTable getAllStudentTable() {
        return allStudentTable;
    }
}
