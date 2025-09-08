package com.uade.tpo.marketplace.controller.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;

    private String email;

    private LocalDateTime date;

    private Double totalPrice;

    private String address;

    private List<OrderDetailResponse> orderDetailResponses;
}
