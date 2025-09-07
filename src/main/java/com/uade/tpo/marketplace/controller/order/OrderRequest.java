package com.uade.tpo.marketplace.controller.order;

import com.uade.tpo.marketplace.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Double totalPrice;

    private String address;

    private List<OrderDetail> orderDetails;
}
