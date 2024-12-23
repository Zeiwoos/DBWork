package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入订单
    public void insertOrder(Order order) {
        System.out.println(order.getStatus());
        String sql = "INSERT INTO orders (CustomerID, OrderDate, TotalAmount, ShippingAddress, Status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getCustomerId(), order.getOrderDate(), order.getTotalAmount(),
                order.getShippingAddress(), order.getStatus());

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
            order.setStatus(Order.OrderStatus.valueOf(rs.getString("Status")));

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
            order.setStatus(Order.OrderStatus.valueOf(rs.getString("Status")));
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
            order.setStatus(Order.OrderStatus.valueOf(rs.getString("Status")));
            return order;
        });
    }

    // 删除订单
    public void deleteOrder(Integer id) {
        String sql = "DELETE FROM orders WHERE OrderID = ?";
        jdbcTemplate.update(sql, id);
    }
}
