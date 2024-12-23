package org.example.db_work_back.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "purchaseorders")
public class PurchaseOrder {

    public enum PurchaseOrderStatus {
        PENDING, SHIPPED, DELIVERED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseID;

    @JoinColumn(name = "SupplierID")
    private Integer supplierID;

    @Column(name = "Orderdate")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", columnDefinition = "ENUM('Pending', 'Shipped', 'Delivered')", nullable = false)
    private PurchaseOrderStatus status = PurchaseOrderStatus.PENDING;

    // Getters and Setters
    public Integer getPurchaseId() {
        return purchaseID;
    }

    public Integer getSupplierId() {
        return supplierID;
    }

    public void setSupplierId(Integer supplier) {
        this.supplierID = supplier;
    }
    public void setPurchaseId(Integer purchaseID) {
        this.purchaseID = purchaseID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }
}
