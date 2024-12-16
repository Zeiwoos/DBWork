package org.example.db_work_back.repository;

import org.example.db_work_back.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // 可自定义扩展查询方法，如按email查找
    Customer findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
