package view;

import controller.StudentController;
import entity.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentFrame extends JFrame {
    private StudentController studentController = new StudentController();
    private JTable studentTable;  // 学生信息展示表格
    private DefaultTableModel tableModel;

    public StudentFrame() {
        setTitle("学生管理系统");
        setSize(600, 400);
        setLocationRelativeTo(null);  // 居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建表格
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] {"ID", "姓名", "性别", "出生日期", "电话", "地址"});
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("添加学生");
        JButton btnUpdate = new JButton("更新学生");
        JButton btnDelete = new JButton("删除学生");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // 添加组件到窗口
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 加载学生数据
        loadStudentData();

        // 添加按钮事件
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddStudentDialog();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateStudentDialog();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
    }

    // 加载所有学生数据到表格
    private void loadStudentData() {
        List<Student> students = studentController.getAllStudents();
        tableModel.setRowCount(0);  // 清空表格
        for (Student student : students) {
            tableModel.addRow(new Object[] {
                student.getStudentId(), student.getName(), student.getGender(), student.getBirthDate(),
                student.getPhone(), student.getAddress()
            });
        }
    }

    // 弹出对话框添加学生
    private void showAddStudentDialog() {
        JTextField nameField = new JTextField(20);
        JTextField genderField = new JTextField(10);
        JTextField birthDateField = new JTextField(10);
        JTextField phoneField = new JTextField(15);
        JTextField addressField = new JTextField(30);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("性别:"));
        panel.add(genderField);
        panel.add(new JLabel("出生日期 (YYYY-MM-DD):"));
        panel.add(birthDateField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);

        int option = JOptionPane.showConfirmDialog(this, panel, "添加学生", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String gender = genderField.getText();
            String birthDate = birthDateField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            Student student = new Student(0, name, gender, birthDate, phone, address);
            studentController.addStudent(student);
            loadStudentData();
        }
    }

    // 弹出对话框更新学生信息
    private void showUpdateStudentDialog() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要更新的学生", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) studentTable.getValueAt(selectedRow, 0);
        String name = (String) studentTable.getValueAt(selectedRow, 1);
        String gender = (String) studentTable.getValueAt(selectedRow, 2);
        String birthDate = (String) studentTable.getValueAt(selectedRow, 3);
        String phone = (String) studentTable.getValueAt(selectedRow, 4);
        String address = (String) studentTable.getValueAt(selectedRow, 5);

        JTextField nameField = new JTextField(name, 20);
        JTextField genderField = new JTextField(gender, 10);
        JTextField birthDateField = new JTextField(birthDate, 10);
        JTextField phoneField = new JTextField(phone, 15);
        JTextField addressField = new JTextField(address, 30);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("性别:"));
        panel.add(genderField);
        panel.add(new JLabel("出生日期 (YYYY-MM-DD):"));
        panel.add(birthDateField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);

        int option = JOptionPane.showConfirmDialog(this, panel, "更新学生", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Student student = new Student(studentId, nameField.getText(), genderField.getText(),
                    birthDateField.getText(), phoneField.getText(), addressField.getText());
            studentController.updateStudent(student);
            loadStudentData();
        }
    }

    // 删除学生
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的学生", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int studentId = (int) studentTable.getValueAt(selectedRow, 0);
        studentController.deleteStudent(studentId);
        loadStudentData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentFrame().setVisible(true);
            }
        });
    }
}
