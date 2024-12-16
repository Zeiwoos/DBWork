package org.example.db_work_back.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;
    private String publisher;
    private Double price;
    private String keywords;
    private String description;
    private Integer stock;
}
