package com.uade.tpo.marketplace.controller.order;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private Long gameId;

    private Integer quantity;

    private Double unitPrice;
}
