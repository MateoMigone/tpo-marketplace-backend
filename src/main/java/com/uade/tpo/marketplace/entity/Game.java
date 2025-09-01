package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private Double price;

    @Column
    private String type;

    @Column
    private Integer stock;

    @Column
    private String platform;

    @ManyToMany
    @JoinTable(name = "game_category", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
}
