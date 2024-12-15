import java.sql.*;

public class testquery {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hub?useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "383517Cc.";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("连接成功！");

            String query = "SELECT * FROM student GROUP BY credit HAVING credit=AVG()";   //SQL语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // 处理结果集
            while (resultSet.next()) {
                int Sno = resultSet.getInt("Sno");
                int Cno = resultSet.getInt("Cno");
                float grade = resultSet.getFloat("grade");

                System.out.println("Sno: " + Sno + ", Cno: " + Cno + ", grade: " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
