package org.example.db_work_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchasedetails")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchasedetailID;

    @JoinColumn(name = "PurchaseID")
    private Integer purchaseOrder;

    @JoinColumn(name = "BookID")
    private Integer bookID;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    // Getters and Setters
    public Integer getPurchaseDetailID() {
        return purchasedetailID;
    }

    public void setPurchaseDetailID(Integer purchaseDetailID) {
        this.purchasedetailID = purchaseDetailID;
    }

    public Integer getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(Integer purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Integer getBook() {
        return bookID;
    }

    public void setBook(Integer book) {
        this.bookID = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
