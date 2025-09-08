/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.uade.tpo.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.marketplace.entity.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author juanb
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long>{
    List<Game> findByStockGreaterThan(int stock);

    List<Game> findByCategories_Name(String name);

    // Buscar por rango de precios
    List<Game> findByPrecioBetween(Double min, Double max);
    // Buscar juegos con precio menor o igual a un valor
    List<Game> findByPriceLessThanEqual(Double precio);

    // Buscar juegos con precio mayor o igual a un valor
    List<Game> findByPriceGreaterThanEqual(Double precio);

    // Buscar por coincidencia exacta
    List<Game> findByTitle(String title);

    // Buscar ignorando mayúsculas/minúsculas y permitiendo coincidencias parciales
    List<Game> findBytitleContainingIgnoreCase(String title);
}
