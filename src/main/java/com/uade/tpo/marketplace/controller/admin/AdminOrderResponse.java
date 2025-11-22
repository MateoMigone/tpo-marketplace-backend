package com.uade.tpo.marketplace.controller.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderResponse {
    
    private Long id;
    private String userEmail;
    private BigDecimal totalAmount;
    private LocalDateTime creationDate;
    private String status; // Agrega esto si tu entidad Order tiene un Status
    
    // Si necesitas los detalles de los productos, podrías agregar:
    // private List<OrderDetailResponse> items;
}
