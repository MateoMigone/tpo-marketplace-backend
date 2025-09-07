package com.uade.tpo.marketplace.controller.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Integer id;

    private String email;

    private LocalDateTime date;

    private Double totalPrice;

    private String address;

    private String payment;

    private List<OrderDetailResponse> orderDetailResponses;
}
