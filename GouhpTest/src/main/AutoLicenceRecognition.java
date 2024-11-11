package main;

import car.BaseModel;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import com.github.sarxos.webcam.*;
import com.github.sarxos.webcam.util.ImageUtils;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AutoLicenceRecognition {
    private JFrame frame;
    private JLabel imgLabel, resultLabel;
    private Webcam webcam;

    public AutoLicenceRecognition(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(imgLabel, BorderLayout.CENTER);

        resultLabel = new JLabel("�ȴ�����ʶ��...");
        resultLabel.setFont(new Font("����", Font.BOLD, 16));
        frame.add(resultLabel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // ��ʼ������ͷ
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();  // ��ʽ���� open() ������ȷ������ͷ��
            System.out.println("����ͷ��ʼ���ɹ�!");
        } else {
            System.err.println("δ���ҵ�����ͷ!");
            resultLabel.setText("�޷����ӵ�����ͷ");
        }

        // ��ʼ��⳵����ʶ����
        startDetection();
    }

    private void startDetection() {
        new Thread(() -> {
            while (webcam != null && webcam.isOpen()) {
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    imgLabel.setIcon(new ImageIcon(image));
                    imgLabel.revalidate();
                    imgLabel.repaint();

                    // ���ó���ʶ�𷽷�
                    String carPlateInfo = detectAndRecognize(image);
                    if (carPlateInfo != null) {
                        resultLabel.setText(carPlateInfo);
                    } else {
                        resultLabel.setText("û��ʶ�𵽳��ƣ�");
                    }

                    try {
                        Thread.sleep(500); // ÿ��500������һ��
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("δ�ܴ�����ͷ��ȡͼ��!");
                }
            }
        }).start();
    }

    private String detectAndRecognize(BufferedImage image) {
        try {
            // ֱ�ӱ���ͼ���ļ�����ʶ��
            String fileName = "car_picture/" + System.currentTimeMillis();
            WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
            System.out.println("�����ͼ��·��: " + fileName + ".png");
            
            // ���ó���ʶ��ģ��
            String res = BaseModel.licensePlate(fileName + ".png");
            System.out.println("����ʶ����: " + res);

            JSONObject json_res = new JSONObject(res);
            JSONArray words_result = json_res.getJSONArray("words_result");

            if (words_result.length() > 0) {
                JSONObject resultObj = words_result.getJSONObject(0);
                String plateNumber = resultObj.getString("number");
                String plateColor = resultObj.getString("color");

                return "���ƺ��룺" + plateNumber + "��������ɫ��" + plateColor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "����ʶ��ʧ�ܣ�";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoLicenceRecognition("�Զ�����ʶ��ϵͳ"));
    }
}
