package pag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StudentFormPanel extends JPanel {

    private JTextField nameField, phoneField, addressField;
    private JComboBox<String> genderComboBox;
    private JSpinner birthDateSpinner;
    private JButton submitButton;

    public StudentFormPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 姓名输入框
        nameField = createTextFieldPanel("姓名：");
        phoneField = createTextFieldPanel("电话：");
        addressField = createTextFieldPanel("地址：");

        // 性别选择框
        genderComboBox = new JComboBox<>(new String[] { "男", "女" });
        add(createPanelWithLabel("性别：", genderComboBox));

        // 出生日期选择器
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthDateSpinner, "yyyy-MM-dd");
        birthDateSpinner.setEditor(dateEditor);
        add(createPanelWithLabel("出生日期：", birthDateSpinner));

        // 提交按钮
        submitButton = new JButton("提交");
        submitButton.setPreferredSize(new Dimension(150, 40));
        add(submitButton);
    }

    private JPanel createTextFieldPanel(String label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        JTextField textField = new JTextField(20);
        panel.add(textField);
        return panel;
    }

    private JPanel createPanelWithLabel(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    public void addSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getGender() {
        return (String) genderComboBox.getSelectedItem();
    }

    public String getFormattedBirthDate() {
        // return formatted birth date from birthDateSpinner
        return birthDateSpinner.getValue().toString();
    }
}

