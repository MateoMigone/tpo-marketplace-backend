package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column
    private Integer quantity;
}
