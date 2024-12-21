package org.example.db_work_back;


import org.example.db_work_back.controller.CustomerController;
import org.example.db_work_back.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class CustomerControllerTest {

    public static void main(String[] args) {
        // 启动 Spring 应用
        var context = SpringApplication.run(DbWorkBackApplication.class, args);

        // 从 Spring 容器获取 CustomerController Bean
        CustomerController controller = context.getBean(CustomerController.class);

        // 测试注册顾客功能
        testRegisterCustomer(controller);
    }

    private static void testRegisterCustomer(CustomerController controller) {
        // 创建一个新的顾客
        Customer customer = new Customer();
        customer.setCustomerName("John Doe"); // 设置顾客姓名
        customer.setEmail("john@example.com"); // 设置邮箱
        customer.setPhone("1234567890"); // 设置电话
        customer.setAddress("123 Elm Street"); // 设置地址
        customer.setBalance(100.0); // 设置余额
        customer.setCreditLevel(2); // 设置信用等级
        customer.setPassword("securePassword"); // 设置密码

        // 调用控制器方法注册顾客
        ResponseEntity<Customer> response = controller.registerCustomer(customer);

        // 打印测试结果
        if (response.getBody() != null && "John Doe".equals(response.getBody().getCustomerName())) {
            System.out.println("测试通过：注册顾客成功！");
        } else {
            System.err.println("测试失败：注册顾客失败！");
        }
    }
}
