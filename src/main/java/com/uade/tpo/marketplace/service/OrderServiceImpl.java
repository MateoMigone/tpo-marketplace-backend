package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.controller.order.OrderResponse;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse createOrder(User user, OrderRequest orderRequest) {

        LocalDateTime dateTime = LocalDateTime.now();

        Order order = new Order();
        order.setUser(user);
        order.setDate(dateTime);
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setAddress(orderRequest.getAddress());
        order.setOrderDetails(orderRequest.getOrderDetails());

        orderRepository.save(order);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setEmail(user.getEmail());
        orderResponse.setDate(dateTime);
        orderResponse.setTotalPrice(orderRequest.getTotalPrice());
        orderResponse.setAddress(orderRequest.getAddress());
        orderResponse.setOrderDetailRequests(null);

        return orderResponse;
    }
}
