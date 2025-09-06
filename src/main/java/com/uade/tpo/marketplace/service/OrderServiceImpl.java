package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.game.GameRequest;
import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.repository.GameRepository;
import com.uade.tpo.marketplace.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(OrderRequest orderRequest) {


        Order order = new Order();



        return orderRepository.save(order);
    }
}
