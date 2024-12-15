import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DebugDatabase {
    public static void main(String[] args) {
        // 数据库连接信息
        String jdbcUrl = "jdbc:mysql://localhost:3306/OnLineBookStore?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "383517Cc.";

        // SQL 语句
        String dropSQL = "DROP DATABASE IF EXISTS OnLineBookStore";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            System.out.println("成功连接到数据库！");

            // 执行 DROP TABLE 语句
            statement.executeUpdate(dropSQL);
            System.out.println("数据库已成功删除！");

        } catch (Exception e) {
            System.err.println("操作失败: " + e.getMessage());
        }
    }
}
