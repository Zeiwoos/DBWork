package org.example.db_work_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchasedetails")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchasedetailID;

    @ManyToOne
    @JoinColumn(name = "PurchaseID")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "BookID")
    private Book book;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    // Getters and Setters
    public Integer getPurchaseDetailID() {
        return purchasedetailID;
    }

    public void setPurchaseDetailID(Integer purchaseDetailID) {
        this.purchasedetailID = purchaseDetailID;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
