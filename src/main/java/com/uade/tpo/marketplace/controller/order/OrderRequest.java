package com.uade.tpo.marketplace.controller.order;

import lombok.Data;

@Data
public class OrderRequest {

    private Double totalPrice;

    private String address;
}
