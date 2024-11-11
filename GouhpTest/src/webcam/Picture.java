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
    private Timer captureTimer;  // 定时器用于自动捕获图像
    private boolean recognized = false;  // 标记是否已识别到车牌

    public Picture() {
        // 设置窗口和摄像头面板
        window = new JFrame("摄像头");
        webcam = Webcam.getDefault();
        WebcamPanel panel = new WebcamPanel(webcam);
        window.add(panel, BorderLayout.CENTER);

        // 左侧显示车牌识别结果的图片
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        window.add(imgLabel, BorderLayout.WEST);

        // 显示车牌识别状态
        JLabel resultLabel = new JLabel("等待车牌识别...");
        resultLabel.setFont(new Font("宋体", Font.BOLD, 16));
        window.add(resultLabel, BorderLayout.SOUTH);

        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        // 定义定时器，每200毫秒检测一次
        int delay = 200; // 每200毫秒检测一次
        captureTimer = new Timer(delay, e -> captureAndRecognize(imgLabel, resultLabel));
        captureTimer.start();  // 启动定时器
    }

    // 定时器触发的事件，捕捉图像并进行车牌识别
    private void captureAndRecognize(JLabel imgLabel, JLabel resultLabel) {
        if (webcam != null && webcam.isOpen() && !recognized) {  // 检查摄像头是否打开且未识别到车牌
            BufferedImage image = webcam.getImage();
            if (image != null) {
                imgLabel.setIcon(new ImageIcon(image));  // 显示当前捕获的图像
                imgLabel.revalidate();
                imgLabel.repaint();

                // 保存图像文件，用于车牌识别
                String fileName = "car_picture/" + System.currentTimeMillis();
                WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
                String lastFileName = fileName + ".png";
                System.out.println("捕获的图像路径: " + lastFileName);

                // 标记已识别车牌，停止定时器，防止重复捕获
                recognized = true;
                captureTimer.stop();

                // 更新状态信息并调用 LicenceP 执行识别
                resultLabel.setText("检测到车牌，开始识别...");
                new LicenceP("智慧停车管理", lastFileName);  // 调用 LicenceP 类，自动打开识别窗口
            }
        }
    }

    public static void main(String[] args) {
        new Picture();
    }
}
