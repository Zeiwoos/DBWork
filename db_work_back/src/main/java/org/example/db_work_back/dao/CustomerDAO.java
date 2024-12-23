package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入客户
    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customername, email, phone, address, balance, creditlevel, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
//        System.out.println(sql);
//        //    // 输出接收到的customer对象的数据
//        System.out.println("Received customer data: " + customer);
//
//        // 如果你希望输出每个字段，可以手动访问它们：
//        System.out.println("Customer Name: " + customer.getCustomerName());
//        System.out.println("Email: " + customer.getEmail());
//        System.out.println("Phone: " + customer.getPhone());
//        System.out.println("Address: " + customer.getAddress());
//        System.out.println("Balance: " + customer.getBalance());
//        System.out.println("Credit Level: " + customer.getCreditLevel());


//        Integer creditLevel = (customer.getCreditLevel() != null) ? customer.getCreditLevel() : 1;
        jdbcTemplate.update(sql, customer.getCustomerName(), customer.getEmail(), customer.getPhone(),
                customer.getAddress(), 0, 0, customer.getPassword());
    }

    // 通过 ID 查询客户
    public Customer selectCustomerById(Integer id) {
        String sql = "SELECT * FROM customers WHERE customerid = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToCustomer);
    }
    // 更新客户信息
    public void updateCustomer(Integer id, Customer customer) {
        String sql = "UPDATE customers SET CustomerName = ?, Email = ?, Phone = ?, Address = ?, Balance = ?, CreditLevel = ?, Password = ? " +
                "WHERE CustomerID = ?";
        jdbcTemplate.update(sql, customer.getCustomerName(), customer.getEmail(), customer.getPhone(),
                customer.getAddress(), customer.getBalance(), customer.getCreditLevel(), customer.getPassword(), id);
    }
    // 删除客户
    public void deleteCustomer(Integer id) {
        String sql = "DELETE FROM customers WHERE CustomerID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Customer selectCustomerByName(String name) {
        String sql = "SELECT * FROM customers WHERE customername = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{name}, this::mapRowToCustomer);
    }
    // 获取所有客户
    public List<Customer> selectAllCustomers() {
        String sql = "SELECT * FROM customers";
//        System.out.println(sql);
        return jdbcTemplate.query(sql, this::mapRowToCustomer);
    }

    // 映射 SQL 结果到 Customer 对象
    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerID(rs.getInt("customerID"));  // 自动生成的ID
        customer.setCustomerName(rs.getString("customername"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setBalance(rs.getBigDecimal("balance"));
        customer.setCreditLevel(rs.getInt("creditlevel"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }
}
