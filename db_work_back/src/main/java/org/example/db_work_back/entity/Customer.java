package org.example.db_work_back.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerID;

    @Column(name = "customername", nullable = false, length = 50)
    private String customername;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "Phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "Balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "creditlevel", nullable = false)
    private Integer creditlevel;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;

    // Getters and Setters
    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customername;
    }

    public void setCustomerName(String customername) {
        this.customername = customername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getCreditLevel() {
        return creditlevel;
    }

    public void setCreditLevel(Integer creditLevel) {
        this.creditlevel = creditLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
