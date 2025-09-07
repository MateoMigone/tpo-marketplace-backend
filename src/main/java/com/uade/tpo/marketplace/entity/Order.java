package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
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
    private User user;

    @Column
    private LocalDateTime date;

    @Column
    private Double totalPrice;

    @Column
    private String address;

    @OneToMany (mappedBy = "order")
    private List<OrderDetail> orderDetails;
}