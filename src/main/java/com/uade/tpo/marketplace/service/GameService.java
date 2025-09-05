/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.repository.GameRepository;

/**
 *
 * @author juanb
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> editGame(Long id,Game gameDetails) {
        return gameRepository.findById(id).map(Game -> {
            Game.setTitle(gameDetails.getTitle());
            Game.setCategories(gameDetails.getCategories());
            return gameRepository.save(Game);
        });
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

}
