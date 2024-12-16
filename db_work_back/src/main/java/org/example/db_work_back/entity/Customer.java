package org.example.db_work_back.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String customerName;
    private String email;
    private String phone;
    private String address;
    private Double balance;
    private Integer creditLevel;
    private String password;
}
