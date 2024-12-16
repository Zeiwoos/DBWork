package org.example.db_work_back.repository;

import org.example.db_work_back.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}