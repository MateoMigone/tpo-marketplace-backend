package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.controller.order.OrderResponse;
import com.uade.tpo.marketplace.entity.User;

public interface OrderService {
    public OrderResponse createOrder(User user, OrderRequest orderRequest);
}
