package org.example.db_work_back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // 自动生成getters, setters, toString, equals, hashCode等
@NoArgsConstructor // 自动生成无参构造函数
@AllArgsConstructor // 自动生成全参构造函数
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Integer customerID;

    @Column(nullable = false, length = 50) // 非空，最大长度50
    private String customerName;

    @Column(nullable = false, unique = true, length = 100) // 非空，唯一，最大长度100
    private String email;

    @Column(nullable = false, length = 20) // 非空，最大长度20
    private String phone;

    @Column(nullable = false, columnDefinition = "TEXT") // 非空，大文本
    private String address;

    @Column(nullable = false, precision = 10, scale = 2) // 默认值0.00，支持小数点后两位
    private Double balance = 0.00;

    @Column(nullable = false) // 默认信用等级为1
    private Integer creditLevel = 1;

    @Column(nullable = false, length = 100) // 非空，最大长度100
    private String password;

}
