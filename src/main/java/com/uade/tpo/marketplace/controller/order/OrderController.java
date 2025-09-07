package com.uade.tpo.marketplace.controller.order;

import com.uade.tpo.marketplace.controller.category.CategoryRequest;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.service.OrderService;
import com.uade.tpo.marketplace.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequest orderRequest){
        Order result = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(result);
    }
}
