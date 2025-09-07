package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;

    @Column
    private LocalDate date;

    @Column
    private Double totalPrice;

    @Column
    private boolean paid;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToMany (mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

