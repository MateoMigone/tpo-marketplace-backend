/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.game.GameRequest;
import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.exceptions.InvalidDiscountException;
import com.uade.tpo.marketplace.exceptions.NegativePriceException;
import com.uade.tpo.marketplace.exceptions.NegativeStockException;

/**
 *
 * @author juanb
 */

public interface GameService {

    public Game createGame(GameRequest gameRequest) throws NegativeStockException, InvalidDiscountException, NegativePriceException;
    public Game editGame(Long id,GameRequest gameRequest) throws NegativeStockException, NegativePriceException, InvalidDiscountException;
    public void deleteGame(Long id);
    public Game getGameById(Long id);
    public java.util.List<Game> getAllGames();
    public java.util.List<Game> getAllAvailableGames();
    public java.util.List<Game> getGamesByCategory(String category);
    public java.util.List<Game> findByRangePrice(Double min, Double max);
    public java.util.List<Game> findByPriceMax(Double max);
    public java.util.List<Game> findByPriceMin(Double min);
    public java.util.List<Game> findByTitle(String title);

}
