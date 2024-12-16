import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class databaseCreate {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "000000";
        String sqlFilePath = "src/test.sql";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))) {

            System.out.println("连接成功！");
            StringBuilder sql = new StringBuilder();
            String line;

            // 逐行读取 SQL 文件并拼接为单个 SQL 语句
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    // 忽略空行或注释
                    continue;
                }
                sql.append(line);
                // 如果遇到分号，表示 SQL 语句结束
                if (line.endsWith(";")) {
                    statement.execute(sql.toString());
                    sql.setLength(0); // 清空 StringBuilder
                }
            }

            System.out.println("SQL 文件已成功执行！");
        } catch (IOException e) {
            System.err.println("读取 SQL 文件失败: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("执行 SQL 文件失败: " + e.getMessage());
        }
    }
}
