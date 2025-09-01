package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Order order;

    @Column
    private String method;

    @Column
    private Double amount;

    @Column
    private LocalDateTime date;
}
