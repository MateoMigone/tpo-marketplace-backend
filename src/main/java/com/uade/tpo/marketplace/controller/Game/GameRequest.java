/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.controller.Game;

import java.util.List;

import com.uade.tpo.marketplace.entity.Category;

import lombok.Data;

@Data
public class GameRequest {
    private Long id;
    private String title;
    private Double price;
    private String type;
    private String platform;
    private String imageUrl;
    private Integer stock;
    private List<Category> categories;
}
