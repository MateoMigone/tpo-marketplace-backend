/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.repository.GameRepository;

/**
 *
 * @author juanb
 */
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @SuppressWarnings("override")
    public Game createGame(String title, Double price, String type, String platform, List categories, String imageUrl, Integer stock) {
        Game game = new Game();
        game.setTitle(title);
        game.setPrice(price);
        game.setType(type);
        game.setPlatform(platform);
        game.setCategories(categories);
        game.setImageUrl(imageUrl);
        game.setStock(stock);
        return gameRepository.save(game);
    }

    @SuppressWarnings("override")
    public Game editGame(Long id, Game gameDetails) {
        return gameRepository.findById(id).map(game -> {
            game.setTitle(gameDetails.getTitle());
            game.setPrice(gameDetails.getPrice());
            game.setType(gameDetails.getType());
            game.setPlatform(gameDetails.getPlatform());
            game.setCategories(gameDetails.getCategories());
            game.setImageUrl(gameDetails.getImageUrl());
            game.setStock(gameDetails.getStock());
            
            return gameRepository.save(game);
        }).orElse(null);
    }

    @SuppressWarnings("override")
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    @SuppressWarnings("override")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }


}
