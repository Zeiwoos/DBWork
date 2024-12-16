package org.example.db_work_back.service;


import org.example.db_work_back.entity.Customer;
import org.example.db_work_back.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found!"));
    }
}
