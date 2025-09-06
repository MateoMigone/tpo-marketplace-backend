/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controller.Game.GameRequest;
import com.uade.tpo.marketplace.entity.Game;

/**
 *
 * @author juanb
 */

public interface GameService {

    public Game createGame(GameRequest gameRequest);
    public Game editGame(Long id,GameRequest gameRequest);
    public void deleteGame(Long id);
    public java.util.List<Game> getAllGames();

}
