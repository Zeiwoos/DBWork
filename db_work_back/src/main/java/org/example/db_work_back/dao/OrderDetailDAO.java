package org.example.db_work_back.dao;

import org.example.db_work_back.entity.OrderDetail;
import org.example.db_work_back.entity.Order;
import org.example.db_work_back.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderDetailDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // 插入订单详情
// 插入订单详情
    public void insertOrderDetail(OrderDetail detail) {
//        System.out.println(detail.getOrder());
        String sql = "INSERT INTO orderdetails (OrderID, BookID, Quantity, Price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                detail.getOrder(),
                detail.getBookId(),
                detail.getQuantity(),
                detail.getPrice());
//        System.out.println("\n"+detail.getOrder());
    }


    // 更新订单详情
    public void updateOrderDetail(OrderDetail detail) {
        String sql = "UPDATE orderdetails SET BookID = ?, Quantity = ?, Price = ? WHERE OrderDetailID = ?";
        jdbcTemplate.update(sql,
                detail.getBookId(),
                detail.getQuantity(),
                detail.getPrice(),
                detail.getOrderDetailID());
    }

    // 删除订单详情
    public void deleteOrderDetail(Integer orderDetailID) {
        String sql = "DELETE FROM orderdetails WHERE OrderDetailID = ?";
        jdbcTemplate.update(sql, orderDetailID);
    }

    // 查询订单详情通过 ID
    public OrderDetail selectOrderDetailById(Integer orderDetailID) {
        String sql = "SELECT * FROM orderdetails WHERE OrderDetailID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderDetailID}, (rs, rowNum) -> {
            OrderDetail detail = new OrderDetail();
            detail.setOrderDetailID(rs.getInt("OrderDetailID"));

            // 设置关联的 OrderID
            detail.setOrder(rs.getInt("OrderID"));

            // 设置关联的 BookID
            detail.setBookId(rs.getInt("BookID"));

            detail.setQuantity(rs.getInt("Quantity"));
            detail.setPrice(rs.getBigDecimal("Price"));
            return detail;
        });
    }

    // 查询某订单的所有详情
    public List<OrderDetail> selectOrderDetailsByOrderId(Integer orderId) {
        String sql = "SELECT * FROM orderdetails WHERE OrderID = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> {
            OrderDetail detail = new OrderDetail();
            detail.setOrderDetailID(rs.getInt("OrderDetailID"));

            // 设置关联的 OrderID

            detail.setOrder(rs.getInt("OrderID"));

            // 设置关联的 BookID

            detail.setBookId(rs.getInt("BookID"));

            detail.setQuantity(rs.getInt("Quantity"));
            detail.setPrice(rs.getBigDecimal("Price"));
            return detail;
        });
    }
}
