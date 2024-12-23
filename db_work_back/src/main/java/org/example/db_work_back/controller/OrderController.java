package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.db_work_back.entity.Order;
import org.example.db_work_back.service.OrderService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrders")
    public Result<List<Order>> getAllOrders() {
        return Result.success(orderService.getAllOrders());
    }

    @PostMapping("/create")
    public Result createOrder(@RequestBody Order order) {
        return Result.success(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Integer id) {
        return Result.success(orderService.getOrderById(id));
    }

    @GetMapping("/customer/{customerid}")
    public Result<List<Order>> getOrderByCustomerId(@PathVariable Integer customerid) {
        return Result.success(orderService.getOrderByCustomerId(customerid));
    }

    @PutMapping("/updateStatus/{id}")
    public Result updateOrderStatus(@PathVariable Integer id, @PathVariable Order.OrderStatus status) {
        Order order = orderService.getOrderById(id);
        order.setStatus(status);
        return Result.success(orderService.updateOrder(order));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }
}
