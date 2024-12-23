package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.db_work_back.entity.Customer;
import org.example.db_work_back.dao.CustomerDAO;
import org.example.db_work_back.result.Result;
import org.example.db_work_back.dto.CustomerLoginDTO;
import org.example.db_work_back.vo.CustomerLoginVO;
import org.example.db_work_back.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Api(tags = "客户相关接口")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAllCustomer")
    public Result<List<Customer>> getAllCustomers() {
        return Result.success(customerService.getAllCustomers());
    }

    @PostMapping("/register")
    public Result registerCustomer(@RequestBody Customer customer) {
        return Result.success(customerService.addCustomer(customer));
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody CustomerLoginDTO userLoginDTO) {
        // 用户登录验证
        Customer user = customerService.login(userLoginDTO);

        // 创建用户返回对象
        CustomerLoginVO userLoginVO = new CustomerLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);

        // 返回用户信息，不涉及 JWT
        return Result.success(userLoginVO);
    }

    @GetMapping("/{id}")
    public Result<Customer> getCustomerById(@PathVariable Integer id) {
        return Result.success(customerService.getCustomerById(id));
    }

    // 更新客户信息
    @PutMapping("/updateBalance/{id}")
    public Result updateCustomer(@PathVariable Integer id, @RequestParam BigDecimal balance) {
        Customer customer = customerService.getCustomerById(id);
        customer.setBalance(balance);
        return customerService.updateCustomer(id, customer);
    }

    // 删除客户
    @DeleteMapping("/delete/{id}")
    public Result deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomer(id);
    }

}
