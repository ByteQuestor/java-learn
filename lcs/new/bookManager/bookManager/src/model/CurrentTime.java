package model;

import java.util.Date;

public class CurrentTime {
	public static String CurrentTime() {
        // 获取当前时间的毫秒数
        long currentTimeMillis = System.currentTimeMillis();
        // 通过有参构造函数创建Date对象
        Date date = new Date(currentTimeMillis);

        // 输出Date对象对应的时间，使用toString方法会按照默认格式输出时间信息
        System.out.println("使用System.currentTimeMillis()结合有参Date构造函数获取的当前时间： " + date);

        // 你还可以进一步获取时间的各个部分进行格式化输出，比如获取年、月、日等（以下是简单示例）
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insertCurrentTime = sdf.format(date);
        System.out.println("自身---格式化后的时间： " + insertCurrentTime);
        return insertCurrentTime;
    }
}
