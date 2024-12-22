package org.example.db_work_back.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipmentID;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @Column(name = "Shipdate")
    private Date shipDate;

    @Column(name = "Trackingnumber", length = 100)
    private String trackingNumber;

    @Column(name = "Status", length = 20)
    private String status;

    // Getters and Setters
    public Integer getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(Integer shipmentID) {
        this.shipmentID = shipmentID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
