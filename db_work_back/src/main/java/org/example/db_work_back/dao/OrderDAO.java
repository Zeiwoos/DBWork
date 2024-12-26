package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入订单
    public void insertOrder(Order order) {
        String sql = "INSERT INTO orders (CustomerID, OrderDate, TotalAmount, ShippingAddress, Status) VALUES (?, ?, ?, ?, ?)";

        // 使用 KeyHolder 获取自动生成的 ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCustomerId());
            ps.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setBigDecimal(3, order.getTotalAmount());
            ps.setString(4, order.getShippingAddress());
            ps.setString(5, order.getStatus());
            return ps;
        }, keyHolder);

        // 设置 Order 实体中的 OrderID
        order.setOrderId(keyHolder.getKey().intValue());
    }

    // 更新订单
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET CustomerID = ?, OrderDate = ?, TotalAmount = ?, ShippingAddress = ?, " +
                "Status = ? WHERE OrderID = ?";
        jdbcTemplate.update(sql, order.getCustomerId(), order.getOrderDate(), order.getTotalAmount(),
                order.getShippingAddress(), order.getStatus(), order.getOrderId());
    }

    // 查询订单通过ID
    public Order selectOrderById(Integer id) {
        String sql = "SELECT * FROM orders WHERE OrderID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderID"));
            order.setCustomerId(rs.getInt("CustomerID"));
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
            order.setShippingAddress(rs.getString("ShippingAddress"));
            order.setStatus(rs.getString("Status"));

            return order;
        });
    }
    public List<Order> selectInvalidOrders() {
        String sql = "SELECT * FROM orders WHERE totalAmount = 0";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderID"));
            order.setCustomerId(rs.getInt("CustomerID"));
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
            order.setShippingAddress(rs.getString("ShippingAddress"));
            order.setStatus(rs.getString("Status"));
            return order;
        });
    }

    // 获取所有订单
    public List<Order> selectAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderID"));
            order.setCustomerId(rs.getInt("CustomerID"));
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
            order.setShippingAddress(rs.getString("ShippingAddress"));
            order.setStatus(rs.getString("Status"));
            return order;
        });
    }

    // 根据顾客ID查询订单
    public List<Order> selectOrdersByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM orders WHERE CustomerID = ?";
        return jdbcTemplate.query(sql, new Object[]{customerId}, (rs, rowNum) -> {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderID"));
            order.setCustomerId(rs.getInt("CustomerID"));
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
            order.setShippingAddress(rs.getString("ShippingAddress"));
            order.setStatus(rs.getString("Status"));
            return order;
        });
    }

    // 删除订单
    public void deleteOrder(Integer id) {
        String sql = "DELETE FROM orders WHERE OrderID = ?";
        jdbcTemplate.update(sql, id);
    }
}
