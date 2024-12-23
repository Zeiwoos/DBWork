package org.example.db_work_back.entity;

import io.swagger.models.auth.In;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailID;

    @JoinColumn(name = "OrderID")
    private Integer orderID;

    @JoinColumn(name = "BookID")
    private Integer bookID;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Price", precision = 10, scale = 2)
    private BigDecimal price;


    // Getters and Setters
    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Integer getOrder() {
        return orderID;
    }

    public void setOrder(Integer order) {
        this.orderID = order;
    }

    public Integer getBookId() {
        return bookID;
    }

    public void setBookId(Integer book) {
        this.bookID = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
