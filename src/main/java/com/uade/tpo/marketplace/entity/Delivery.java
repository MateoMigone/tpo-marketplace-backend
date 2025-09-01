package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrega")
    private DeliveryType deliveryType;

    @Column
    private String address;

    @Column
    private String downloadLink;
}
