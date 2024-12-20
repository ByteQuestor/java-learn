package school;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLFileExecutor {

    /**
     * 执行指定的 SQL 文件内容
     *
     * @param connection 数据库连接
     * @param filePath SQL 文件路径
     */
    public static void executeSQLFile(Connection connection, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Statement statement = connection.createStatement()) {

            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过空行和注释
                if (line.trim().isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sqlBuilder.append(line);
                // SQL 语句以分号结束
                if (line.endsWith(";")) {
                    statement.execute(sqlBuilder.toString());
                    sqlBuilder.setLength(0); // 清空 StringBuilder
                }
            }
            System.out.println("SQL 文件执行完成！");
        } catch (IOException e) {
            System.err.println("读取 SQL 文件失败：" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("执行 SQL 语句失败：" + e.getMessage());
        }
    }
}
