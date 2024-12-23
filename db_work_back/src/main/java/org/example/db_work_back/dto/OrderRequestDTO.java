package org.example.db_work_back.dto;

import org.example.db_work_back.entity.Order;
import org.example.db_work_back.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequestDTO {
    private Order order;
    private List<OrderDetail> orderDetails;

    public void setOrderTotalprice(){
        order.setTotalAmount(new BigDecimal("0"));

        for (OrderDetail orderDetail : orderDetails) {
//            System.out.println(orderDetail.getPrice());
            order.setTotalAmount(order.getTotalAmount().add(orderDetail.getPrice()));
        }
    }

    public void setOrderTotalpriceINI(){
        order.setTotalAmount(new BigDecimal("0"));

    }
    // Getters and Setters
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
