package org.example.db_work_back.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "orders")
public class Order {
    // 定义枚举类型，表示订单状态
    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;

    @JoinColumn(name = "CustomerID")
    private Integer customerID;

    @Column(name = "Orderdate")
    private Date orderDate;

    @Column(name = "Totalamount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "Shippingaddress", columnDefinition = "TEXT")
    private String shippingAddress;

    // 使用枚举类型 OrderStatus 映射数据库中的 ENUM 类型
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('PENDING', 'SHIPPED', 'DELIVERED') DEFAULT 'PENDING'")
    private OrderStatus status ;  // 默认值设置为 PENDING

    // Getters and Setters
    public Integer getOrderId() {
        return orderID;
    }

    public void setOrderId(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getCustomerId() {
        return customerID;
    }

    public void setCustomerId(Integer customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
