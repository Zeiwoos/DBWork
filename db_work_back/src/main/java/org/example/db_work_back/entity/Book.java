package org.example.db_work_back.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookID;

    @Column(name = "Title", nullable = false, length = 200)
    private String title;

    @Column(name = "Author", length = 200)
    private String author;

    @Column(name = "Publisher", length = 100)
    private String publisher;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Keywords", columnDefinition = "TEXT")
    private String keywords;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;


//    @Lob
//    @Column(name = "CoverImage")
//    private byte[] coverImage;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Storagelocation", columnDefinition = "TEXT")
    private String storageLocation;

    @JoinColumn(name = "SeriesID")
    private Integer series;

    @JoinColumn(name = "SupplierID", columnDefinition = "TEXT")
    private Integer SupplierID;
    // Getters and Setters
    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(Integer SupplierID) {
        this.SupplierID = SupplierID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public byte[] getCoverImage() {
//        return coverImage;
//    }
//
//    public void setCoverImage(byte[] coverImage) {
//        this.coverImage = coverImage;
//    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public int getSeriesID() {
        return series;
    }

    public void setSeriesID(int seriesID) {
        this.series=seriesID;
    }
}
