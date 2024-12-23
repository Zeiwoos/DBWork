package org.example.db_work_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierID;

    @Column(name = "Suppliername", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "Contactname", length = 50)
    private String contactName;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    // Getters and Setters
    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
