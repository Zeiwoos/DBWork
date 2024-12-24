package org.example.db_work_back.service;
import org.springframework.transaction.annotation.Transactional;
import org.example.db_work_back.entity.OrderDetail;
import org.example.db_work_back.repository.OrderDetailRepository;
import org.example.db_work_back.dao.OrderDAO;
import org.example.db_work_back.dao.BookDAO;
import org.example.db_work_back.dao.OrderDetailDAO;
import org.example.db_work_back.dto.OrderRequestDTO;
import org.example.db_work_back.entity.Order;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {


    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private  OrderDetailDAO orderDetailDAO;
    @Autowired
    private  BookDAO bookDAO;

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
        List<OrderDetail> orderDetails = orderDetailDAO.selectOrderDetailsByOrderId(id);
        for (OrderDetail detail : orderDetails) {
            Integer detailID = detail.getOrderDetailID();
            orderDetailDAO.deleteOrderDetail(detailID); // 使用 OrderDetailDAO 插入订单详情
        }
        orderDAO.deleteOrder(id);
        return Result.success("订单删除成功");
    }

    // 创建订单及详情
    @Transactional
    public Result createOrderWithDetails(OrderRequestDTO orderRequest) {
        // 保存订单
        Order order = orderRequest.getOrder();
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        orderRequest.setOrderTotalpriceINI();
        orderDAO.insertOrder(order); // 使用 OrderDAO 插入订单
//        System.out.println(order);
        // 获取生成的订单 ID（假设插入后 ID 可用）
        Integer orderId = order.getOrderId();

//        System.out.println("main "+orderId + " total : " + order.getTotalAmount());
        // 保存订单详情
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails();
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(orderId); // 关联订单
            Integer bookid=detail.getBookId();
            Integer number=detail.getQuantity();
            detail.setPrice(bookDAO.selectBookById(bookid).getPrice().multiply(new BigDecimal(number)));
//            detail.setPrice(new BigDecimal("0"));
//            System.out.println("number "+detail.getPrice());
            orderDetailDAO.insertOrderDetail(detail); // 使用 OrderDetailDAO 插入订单详情
        }
        orderRequest.setOrderTotalprice();
        orderDAO.updateOrder(order);
        return Result.success(orderRequest);
    }
}