package org.example.db_work_back.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "orders")
public class Order {
    // 定义枚举类型，表示订单状态

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
    @Column(name = "Status",  length = 50)
    private String status = "PENDING" ;  // 默认值设置为 PENDING

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

    public String  getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
