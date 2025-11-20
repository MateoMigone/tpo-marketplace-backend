package com.uade.tpo.marketplace.controller.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetailRequest> itemList;
}
