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

    public LicenceP(String name, String imagePath) {  // ���� imagePath ����
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle(name);
        f.setBounds(100, 100, 1000, 600);
        f.setLayout(null);

        title = new JLabel("�ǻ�ͣ������----����ʶ��");
        title.setBounds(200, 10, 600, 80);
        title.setFont(new Font("����", Font.BOLD, 36));
        f.add(title);

        img = new JLabel("");
        img.setBounds(100, 100, 350, 350);
        f.add(img);

        result = new JTextArea("");
        result.setFont(new Font("����", Font.BOLD, 16));
        result.setBounds(500, 100, 350, 350);
        f.add(result);

        JButton recong = new JButton("����ʶ��");
        recong.setFont(new Font("����", Font.BOLD, 16));
        recong.setBounds(500, 460, 150, 40);
        recong.addActionListener(this);
        f.add(recong);

        f.setVisible(true);

        // �������պ��ͼ��
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
            String res = BaseModel.licensePlate(path);  // ����ͼƬ·��
            JSONObject json_res = new JSONObject(res);
            JSONArray words_result = json_res.getJSONArray("words_result");
            JSONObject results = words_result.getJSONObject(0);  // ��ȡ��һ�����ƽ��
            if (results != null) {
                result.setText("���ƺ��룺" + results.getString("number") + "\n������ɫ��" + results.getString("color"));
            } else {
                result.setText("û��ʶ�𵽴˳��ƣ�");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setText("ʶ��ʧ�ܣ�");
        }
    }
}
