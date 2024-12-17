package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanelFactory {

    public JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Border panelBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title);
        panel.setBorder(panelBorder);
        panel.add(new JLabel(title + "界面"), BorderLayout.CENTER);
        return panel;
    }
}
