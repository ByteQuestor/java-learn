package car;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.io.File;

public class LicenceP {
    JFrame f;
    JLabel title, img;
    JTextArea result;
    String path;

    public LicenceP(String name, String imagePath) {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle(name);
        f.setBounds(100, 100, 1000, 600);
        f.setLayout(null);

        title = new JLabel("智慧停车管理----车牌识别");
        title.setBounds(200, 10, 600, 80);
        title.setFont(new Font("宋体", Font.BOLD, 36));
        f.add(title);

        img = new JLabel("");
        img.setBounds(100, 100, 350, 350);
        f.add(img);

        result = new JTextArea("");
        result.setFont(new Font("宋体", Font.BOLD, 16));
        result.setBounds(500, 100, 350, 350);
        f.add(result);

        f.setVisible(true);

        // 加载拍照后的图像
        path = imagePath;
        if (path != null) {
            ImageIcon imageIcon = new ImageIcon(path);
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT));
            img.setIcon(imageIcon);
        }

        // 启动窗口后立即进行车牌识别
        recognizePlate();
    }

    // 识别车牌并更新结果显示
    private void recognizePlate() {
        try {
            String res = BaseModel.licensePlate(path);  // 传入图片路径
            JSONObject json_res = new JSONObject(res);
            JSONArray words_result = json_res.getJSONArray("words_result");
            JSONObject results = words_result.getJSONObject(0);  // 获取第一个车牌结果
            if (results != null) {
                result.setText("车牌号码：" + results.getString("number") + "\n车牌颜色：" + results.getString("color"));
            } else {
                result.setText("没有识别到此车牌！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setText("识别失败！");
        }
    }
}
