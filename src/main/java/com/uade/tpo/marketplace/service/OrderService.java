package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.controller.order.OrderResponse;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.NoStockAvailableException;
import java.util.List;

public interface OrderService {

  
    public OrderResponse createOrder(User user, OrderRequest orderRequest) throws NoStockAvailableException;

    public List<OrderResponse> getOrdersByUserEmail(String email);

}
