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

        resultLabel = new JLabel("等待车牌识别...");
        resultLabel.setFont(new Font("宋体", Font.BOLD, 16));
        frame.add(resultLabel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // 初始化摄像头
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(new Dimension(640, 480));
            webcam.open();  // 显式调用 open() 方法来确保摄像头打开
            System.out.println("摄像头初始化成功!");
        } else {
            System.err.println("未能找到摄像头!");
            resultLabel.setText("无法连接到摄像头");
        }

        // 开始检测车辆并识别车牌
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

                    // 调用车牌识别方法
                    String carPlateInfo = detectAndRecognize(image);
                    if (carPlateInfo != null) {
                        resultLabel.setText(carPlateInfo);
                    } else {
                        resultLabel.setText("没有识别到车牌！");
                    }

                    try {
                        Thread.sleep(500); // 每隔500毫秒检测一次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("未能从摄像头获取图像!");
                }
            }
        }).start();
    }

    private String detectAndRecognize(BufferedImage image) {
        try {
            // 直接保存图像文件用于识别
            String fileName = "car_picture/" + System.currentTimeMillis();
            WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
            System.out.println("捕获的图像路径: " + fileName + ".png");
            
            // 调用车牌识别模块
            String res = BaseModel.licensePlate(fileName + ".png");
            System.out.println("车牌识别结果: " + res);

            JSONObject json_res = new JSONObject(res);
            JSONArray words_result = json_res.getJSONArray("words_result");

            if (words_result.length() > 0) {
                JSONObject resultObj = words_result.getJSONObject(0);
                String plateNumber = resultObj.getString("number");
                String plateColor = resultObj.getString("color");

                return "车牌号码：" + plateNumber + "，车牌颜色：" + plateColor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "车牌识别失败！";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoLicenceRecognition("自动车牌识别系统"));
    }
}
