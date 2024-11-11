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
    private String lastFileName;  // 保存最后拍摄图片的路径
    private Timer autoCaptureTimer;  // 定时器用于自动拍照

    public Picture() {
        window = new JFrame("摄像头");
        webcam = Webcam.getDefault();
        WebcamPanel panel = new WebcamPanel(webcam);
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("拍照");
        button.addActionListener(this);
        window.add(panel, BorderLayout.CENTER);
        window.add(button, BorderLayout.SOUTH);

        window.setResizable(true);
        window.pack();
        window.setVisible(true);

        // 定义定时器，每10秒自动拍照一次
        int delay = 10000; // 10000 毫秒，即 10 秒
        autoCaptureTimer = new Timer(delay, e -> takePicture());
        autoCaptureTimer.start(); // 启动定时器
    }

    // 手动拍照按钮触发的事件
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            takePicture();
        }
    }

    // 拍照并调用 LicenceP 进行识别
    private void takePicture() {
        String fileName = "car_picture/" + System.currentTimeMillis(); // 保存路径
        WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
        lastFileName = fileName + ".png";
        System.out.println("捕获的图像路径: " + lastFileName);
        JOptionPane.showMessageDialog(null, "拍照成功，路径为: " + lastFileName);

        // 调用 LicenceP 进行识别
        new LicenceP("车牌识别", lastFileName);
    }

    public static void main(String[] args) {
        new Picture();
    }
}
