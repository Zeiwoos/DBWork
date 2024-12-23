package org.example.db_work_back.service;
import org.example.db_work_back.result.Result;
import org.example.db_work_back.dao.CustomerDAO;
import org.example.db_work_back.dto.CustomerLoginDTO;
import org.example.db_work_back.vo.CustomerLoginVO;
import org.example.db_work_back.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerDAO customerDAO;


    public Result<Customer> addCustomer(Customer customer) {
        customerDAO.insertCustomer(customer);
        return Result.success(customer);
    }

    // 根据 ID 获取客户
    public Customer getCustomerById(Integer id) {
        return customerDAO.selectCustomerById(id);
    }

    public Customer login(CustomerLoginDTO customerLoginDTO) {
        // 获取用户的输入的用户ID和密码
        Integer customerID = customerLoginDTO.getCustomerID();
        String password = customerLoginDTO.getPassword();

        // 查找用户
        Customer user = customerDAO.selectCustomerById(customerID);
        if (user == null) {
            // 用户未找到，返回 null 或抛出异常
            throw new RuntimeException("用户不存在");
        }

        // 校验密码
        if (!user.getPassword().equals(password)) {
            // 密码不正确，返回 null 或抛出异常
            throw new RuntimeException("密码错误");
        }

        // 登录成功，返回用户信息
        return user;
    }
    // 获取所有客户
    public List<Customer> getAllCustomers() {
        return customerDAO.selectAllCustomers();
    }
    // 更新客户
    public Result<Customer> updateCustomer(Integer id, Customer customer) {
        customerDAO.updateCustomer(id, customer);
        return Result.success(customer);
    }

    // 删除客户
    public Result<String> deleteCustomer(Integer id) {
        customerDAO.deleteCustomer(id);
        return Result.success("客户删除成功");
    }
}
