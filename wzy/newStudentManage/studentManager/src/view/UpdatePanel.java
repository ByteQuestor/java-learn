package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.DatabaseManager;

import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class UpdatePanel {

    private JPanel panel;

    public UpdatePanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // 初始化表格并加载数据
        JTable table = loadStudentData();
        
        // 将表格添加到面板中
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JTable loadStudentData() {
        // 表格列的标题
        String[] columns = {"ID", "姓名", "年龄", "性别", "班级"};
        
        // 创建一个默认的表格模型
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        // 查询数据库并加载数据
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM students"; // 替换成你的查询语句
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                // 处理查询结果并将数据添加到表格模型中
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("student_id"));
                    row.add(rs.getString("gender"));
                    row.add(rs.getDate("birth_date").toString());
                    row.add(rs.getString("phone"));
                    row.add(rs.getString("address"));
                    row.add(rs.getString("password"));
                   
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // 返回带有数据的JTable
        return new JTable(model);
    }
}
