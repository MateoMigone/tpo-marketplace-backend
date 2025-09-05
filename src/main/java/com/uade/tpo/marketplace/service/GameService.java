/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Game;

/**
 *
 * @author juanb
 */

public interface GameService {

    public Game createGame(String title, Double price, String type, String platform, java.util.List categories, String imageUrl, Integer stock);
    public Game editGame(Long id,Game gameDetails);
    public void deleteGame(Long id);
    public java.util.List<Game> getAllGames();

}
