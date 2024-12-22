package org.example.db_work_back.service;

import org.example.db_work_back.dao.OrderDAO;
import org.example.db_work_back.entity.Order;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    // 获取所有订单
    public List<Order> getAllOrders() {
        return orderDAO.selectAllOrders();
    }

    // 根据ID获取订单
    public Order getOrderById(Integer id) {
        return orderDAO.selectOrderById(id);
    }

    public List<Order> getOrderByCustomerId(Integer id) {
        return orderDAO.selectOrdersByCustomerId(id);
    }

    // 创建新的订单
    public Result createOrder(Order order) {
        try{
            orderDAO.insertOrder(order);
            return Result.success("订单创建成功");
        }
        catch(Exception e){
            return Result.error("订单创建成功"+e.getMessage());
        }
    }

    // 更新订单信息
    public Result updateOrder(Order order) {
        orderDAO.updateOrder(order);
        return Result.success(order);
    }

    // 删除订单
    public Result deleteOrder(Integer id) {
        orderDAO.deleteOrder(id);
        return Result.success("订单删除成功");
    }
}
