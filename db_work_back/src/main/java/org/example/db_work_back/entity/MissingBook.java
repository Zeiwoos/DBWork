package org.example.db_work_back.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "missingBooks")
public class MissingBook {

    public enum missingStatus {
        SOLVED, UNSOLVED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MissingID;

    @JoinColumn(name = "BookID")
    private Integer BookID;

    @JoinColumn(name = "CustomerID")
    private Integer CustomerID;

    @Column(name = "Quantity", nullable = false)
    private Integer Quantity;

    @Column(name = "Registerdate")
    private Date Registerdate;

    @Column(name = "Status",  length = 50)
    private String Status ;

    // Getters and Setters
    public Integer getMissingId() {
        return MissingID;
    }

    public void setMissingId(Integer missingID) {
        this.MissingID = missingID;
    }

    public Integer getBookId() {
        return BookID;
    }

    public void setBookId(Integer book) {
        this.BookID = book;
    }

    public Integer getCustomerId() {
        return CustomerID;
    }

    public void setCustomerId(Integer customer) {
        this.CustomerID = customer;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        this.Quantity = quantity;
    }

    public Date getRegisterDate() {
        return Registerdate;
    }

    public void setRegisterDate(Date registerDate) {
        this.Registerdate = registerDate;
    }

    public String getStatus() {return this.Status;}
    public void setStatus(String status) {this.Status = status;}
}
