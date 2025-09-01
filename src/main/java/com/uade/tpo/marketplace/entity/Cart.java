package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany (mappedBy = "cart")
    private List<CartDetail> cartDetails;
//asdasdasdasdasdasdsadasda
    @Column
    private LocalDate date;

    // 4
}

