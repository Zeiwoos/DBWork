package test.connect;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class testconnector {

    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    private static String dbDriver;

    // 加载配置
    static {
        try (InputStream input = testconnector.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("无法加载数据库配置文件 'application.properties'");
            }

            Properties props = new Properties();
            props.load(input);

            dbUrl = props.getProperty("db.url");
            dbUsername = props.getProperty("db.username");
            dbPassword = props.getProperty("db.password");
            dbDriver = props.getProperty("db.driver");

            // 注册 JDBC 驱动
            Class.forName(dbDriver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("初始化数据库配置失败", e);
        }
    }

    public static void main(String[] args) {
        // 测试数据库连接
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            System.out.println("数据库连接成功！");

            // 执行测试查询
            String testQuery = "SELECT * FROM customers LIMIT 5";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(testQuery)) {

                System.out.println("测试查询结果：");
                while (resultSet.next()) {
                    System.out.println("CustomerID: " + resultSet.getInt("CustomerID") +
                            ", CustomerName: " + resultSet.getString("CustomerName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("数据库连接失败：" + e.getMessage());
        }
    }
}
