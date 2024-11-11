package webcam;

import java.awt.*;
import javax.swing.*;
import com.github.sarxos.webcam.*;
import com.github.sarxos.webcam.util.ImageUtils;

import car.LicenceP;

import java.awt.image.BufferedImage;

public class Picture {
    JFrame window;
    Webcam webcam;
    private Timer captureTimer;  // ��ʱ�������Զ�����ͼ��
    private boolean recognized = false;  // ����Ƿ���ʶ�𵽳���

    public Picture() {
        // ���ô��ں�����ͷ���
        window = new JFrame("����ͷ");
        webcam = Webcam.getDefault();
        WebcamPanel panel = new WebcamPanel(webcam);
        window.add(panel, BorderLayout.CENTER);

        // �����ʾ����ʶ������ͼƬ
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        window.add(imgLabel, BorderLayout.WEST);

        // ��ʾ����ʶ��״̬
        JLabel resultLabel = new JLabel("�ȴ�����ʶ��...");
        resultLabel.setFont(new Font("����", Font.BOLD, 16));
        window.add(resultLabel, BorderLayout.SOUTH);

        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        // ���嶨ʱ����ÿ200������һ��
        int delay = 200; // ÿ200������һ��
        captureTimer = new Timer(delay, e -> captureAndRecognize(imgLabel, resultLabel));
        captureTimer.start();  // ������ʱ��
    }

    // ��ʱ���������¼�����׽ͼ�񲢽��г���ʶ��
    private void captureAndRecognize(JLabel imgLabel, JLabel resultLabel) {
        if (webcam != null && webcam.isOpen() && !recognized) {  // �������ͷ�Ƿ����δʶ�𵽳���
            BufferedImage image = webcam.getImage();
            if (image != null) {
                imgLabel.setIcon(new ImageIcon(image));  // ��ʾ��ǰ�����ͼ��
                imgLabel.revalidate();
                imgLabel.repaint();

                // ����ͼ���ļ������ڳ���ʶ��
                String fileName = "car_picture/" + System.currentTimeMillis();
                WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
                String lastFileName = fileName + ".png";
                System.out.println("�����ͼ��·��: " + lastFileName);

                // �����ʶ���ƣ�ֹͣ��ʱ������ֹ�ظ�����
                recognized = true;
                captureTimer.stop();

                // ����״̬��Ϣ������ LicenceP ִ��ʶ��
                resultLabel.setText("��⵽���ƣ���ʼʶ��...");
                new LicenceP("�ǻ�ͣ������", lastFileName);  // ���� LicenceP �࣬�Զ���ʶ�𴰿�
            }
        }
    }

    public static void main(String[] args) {
        new Picture();
    }
}
