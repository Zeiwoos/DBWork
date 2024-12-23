//package org.example.db_work_back.entity;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "browsingLogs")
//public class BrowsingLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer logID;
//
//    @ManyToOne
//    @JoinColumn(name = "CustomerID")
//    private Customer customer;
//
//    @ManyToOne
//    @JoinColumn(name = "BookID")
//    private Book book;
//
//    @Column(name = "BrowseDate")
//    private Date browseDate;
//
//    // Getters and Setters
////    public Integer getLogID() {
//        return logID;
//    }
//
//    public void setLogID(Integer logID) {
//        this.logID = logID;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
//
//    public Date getBrowseDate() {
//        return browseDate;
//    }
//
//    public void setBrowseDate(Date browseDate) {
//        this.browseDate = browseDate;
//    }
//}
