package com.uade.tpo.marketplace.controller.order;

import com.uade.tpo.marketplace.entity.Game;
import lombok.Data;

@Data
public class OrderDetailResponse {
    private Game game;

    private Integer quantity;

    private Double unitPrice;
}
