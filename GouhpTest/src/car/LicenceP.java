package car;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LicenceP implements ActionListener {
    JFrame f;
    JLabel title, img;
    JTextArea result;
    String path;

    public LicenceP(String name, String imagePath) {  // 新增 imagePath 参数
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

        JButton recong = new JButton("车牌识别");
        recong.setFont(new Font("宋体", Font.BOLD, 16));
        recong.setBounds(500, 460, 150, 40);
        recong.addActionListener(this);
        f.add(recong);

        f.setVisible(true);

        // 加载拍照后的图像
        path = imagePath;
        if (path != null) {
            ImageIcon imageIcon = new ImageIcon(path);
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT));
            img.setIcon(imageIcon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
