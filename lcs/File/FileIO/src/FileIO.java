import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileIO {
    public static void main(String[] args) {
        String text = "If you really want to do something, you'll find a way. If you don't, you'll find an excuse.";
        String chineseText = "如果你真的想做什么事，你会找到方法。如果你不想，那你只能找到借口。";
        // 写入文件字节输出流
        try (
        	FileOutputStream f = new FileOutputStream("English.txt");
        	FileInputStream englishReader = new FileInputStream("English.txt");
        	FileWriter chineseWriter = new FileWriter("Chinese.txt");
        	FileWriter contentWriter = new FileWriter("content.txt", true);
        	FileInputStream chineseReader = new FileInputStream("Chinese.txt")
        		) {
        	//1，写入英文内容
            f.write(text.getBytes());
            System.out.println("内容已写入到 English.txt 文件中");
            
            //2,读取英文内容
            int data;
            while ((data = englishReader.read()) != -1) {
                System.out.print((char) data);
            }
            
            //3,写入中午内容
            chineseWriter.write(chineseText);
            System.out.println("内容已写入到 Chinese.txt 文件中");
            chineseWriter.close();
            // 4. 向 content.txt 中输出 English: <换行>，然后读 English.txt 追加后面，输出 中文: <换行>，然后读 Chinese.txt 内容
            // 写入 English: <换行>
            contentWriter.write("English:\n");
            // 读取 English.txt 并追加
            FileInputStream OutenglishReader = new FileInputStream("English.txt");
            while ((data = OutenglishReader.read()) != -1) {
                contentWriter.write(data);
            }
            contentWriter.write("\n"); // 换行

            // 写入 中文: <换行>
            contentWriter.write("中文:\n");
            //FileInputStream OutchineseReader = new FileInputStream("Chinese.txt");
            InputStreamReader OutchineseReader = new InputStreamReader(new FileInputStream("Chinese.txt"), StandardCharsets.UTF_8);
            
            // 读取 Chinese.txt 并追加
            int chinaData;
            while ((chinaData = OutchineseReader.read()) != -1) {
                contentWriter.write(chinaData);  // 写入中文内容
            }
            contentWriter.write("\n"); // 换行
            System.out.println("内容已写入到 content.txt 文件中");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
