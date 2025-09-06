package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.game.GameRequest;
import com.uade.tpo.marketplace.controller.order.OrderRequest;
import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.Wishlist;

public interface OrderService {
    public Order createOrder(OrderRequest orderRequest);
}
