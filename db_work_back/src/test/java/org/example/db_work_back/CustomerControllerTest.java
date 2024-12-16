package org.example.db_work_back;

import org.example.db_work_back.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private org.example.db_work_back.controller.CustomerController controller;

    @Test
    public void testRegisterCustomer() {
        Customer customer = new Customer();
        customer.setCustomerName("Alice");
        customer.setEmail("alice@example.com");
        ResponseEntity<Customer> response = controller.registerCustomer(customer);

        assertEquals("Alice", response.getBody().getCustomerName());
    }
}
