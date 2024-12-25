package org.example.db_work_back.service;
import io.swagger.models.auth.In;
import org.example.db_work_back.entity.Book;
import org.example.db_work_back.entity.Customer;
import org.springframework.transaction.annotation.Transactional;
import org.example.db_work_back.entity.OrderDetail;
import org.example.db_work_back.repository.OrderDetailRepository;
import org.example.db_work_back.dao.OrderDAO;
import org.example.db_work_back.dao.BookDAO;
import org.example.db_work_back.dao.CustomerDAO;
import org.example.db_work_back.dao.OrderDetailDAO;
import org.example.db_work_back.dto.OrderRequestDTO;
import org.example.db_work_back.entity.Order;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private CustomerDAO customerDAO;

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
        Integer customerId = order.getCustomerId();
//        System.out.println("main "+orderId + " total : " + order.getTotalAmount());
        //算折扣
        Customer customer = customerDAO.selectCustomerById(customerId);
        Double discount = customer.getCreditLevel()*0.05;
        // 保存订单详情
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails();
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(orderId); // 关联订单

            Integer bookid=detail.getBookId();
            Integer number=detail.getQuantity();
            //减库存
            Book thisbook = bookDAO.selectBookById(bookid);
            thisbook.setStock(thisbook.getStock()-number);
            bookDAO.updateBook(bookid,thisbook);

            // 假设 thisbook.getPrice() 是 BigDecimal 类型，number 和 discount 是其他数值
            BigDecimal price = thisbook.getPrice().multiply(new BigDecimal(number)).multiply(new BigDecimal(1 - discount));

// 将结果限制为最多两位小数
            BigDecimal finalPrice = price.setScale(2, RoundingMode.HALF_UP);

// 设置 finalPrice 到 detail 的价格字段
            detail.setPrice(finalPrice);
//            detail.setPrice(new BigDecimal("0"));
//            System.out.println("number "+detail.getPrice());
            orderDetailDAO.insertOrderDetail(detail); // 使用 OrderDetailDAO 插入订单详情
        }

        orderRequest.setOrderTotalprice();
        //大于等于3级的信用等级可以赊账
        if(customer.getCreditLevel()<3&&customer.getBalance().compareTo(orderRequest.getOrderTotalprice()) < 0){
            return Result.error("您的余额不足");
        }

        customer.setBalance(customer.getBalance().subtract(orderRequest.getOrderTotalprice()));
//        System.out.println(customer.getBalance());
        customerDAO.updateCustomer(customerId,customer);
        orderDAO.updateOrder(order);
        return Result.success(orderRequest);
    }
}
