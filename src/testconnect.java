import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testconnect {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hub?useSSL=false&serverTimezone=Asia/Shanghai";
        // 替换为你的数据库URL
        String user = "root"; // 替换为你的用户名
        String password = "383517Cc."; // 替换为你的密码

        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC驱动已加载！");

            // 尝试连接到数据库
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("成功连接到数据库！");
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("无法加载JDBC驱动：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
        }
    }
}
