package webcam;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.github.sarxos.webcam.*;
import com.github.sarxos.webcam.util.ImageUtils;
import car.LicenceP;

public class Picture implements ActionListener {
    JFrame window;
    JButton button;
    Webcam webcam;
    private String lastFileName;  // �����������ͼƬ��·��
    private Timer autoCaptureTimer;  // ��ʱ�������Զ�����

    public Picture() {
        window = new JFrame("����ͷ");
        webcam = Webcam.getDefault();
        WebcamPanel panel = new WebcamPanel(webcam);
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("����");
        button.addActionListener(this);
        window.add(panel, BorderLayout.CENTER);
        window.add(button, BorderLayout.SOUTH);

        window.setResizable(true);
        window.pack();
        window.setVisible(true);

        // ���嶨ʱ����ÿ10���Զ�����һ��
        int delay = 10000; // 10000 ���룬�� 10 ��
        autoCaptureTimer = new Timer(delay, e -> takePicture());
        autoCaptureTimer.start(); // ������ʱ��
    }

    // �ֶ����հ�ť�������¼�
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            takePicture();
        }
    }

    // ���ղ����� LicenceP ����ʶ��
    private void takePicture() {
        String fileName = "car_picture/" + System.currentTimeMillis(); // ����·��
        WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
        lastFileName = fileName + ".png";
        System.out.println("�����ͼ��·��: " + lastFileName);
        JOptionPane.showMessageDialog(null, "���ճɹ���·��Ϊ: " + lastFileName);

        // ���� LicenceP ����ʶ��
        new LicenceP("����ʶ��", lastFileName);
    }

    public static void main(String[] args) {
        new Picture();
    }
}
