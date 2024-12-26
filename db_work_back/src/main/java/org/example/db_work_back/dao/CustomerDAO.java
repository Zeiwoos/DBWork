package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customername, email, phone, address, balance, creditlevel, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        // 创建 KeyHolder 来保存生成的主键
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 使用 jdbcTemplate.update 以便获取生成的主键
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setDouble(5, 0);  // balance 默认为 0
            ps.setInt(6, 1);     // creditlevel 默认为 1
            ps.setString(7, customer.getPassword());
            return ps;
        }, keyHolder);

        // 获取生成的 ID
        Number generatedId = keyHolder.getKey();
        customer.setCustomerID(generatedId.intValue());  // 将生成的 ID 设置到 Customer 对象中
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
