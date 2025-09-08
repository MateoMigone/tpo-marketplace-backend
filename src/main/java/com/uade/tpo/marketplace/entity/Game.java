package com.uade.tpo.marketplace.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

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
    private Double discount;

    @Column
    private Integer stock;

    @Column
    private String platform;

    @Column
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "game_category", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    public Double getFinalPrice (){
        return price - (price * discount);
    }

}
