/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.service;

import java.util.HashSet;
import java.util.List;

import com.uade.tpo.marketplace.controller.game.GameRequest;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.repository.CategoryRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;

    @SuppressWarnings("override")
    public Game createGame(GameRequest gameRequest) {
        HashSet<Long> requestedIds = new HashSet<>(gameRequest.getCategoriesIds());
        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(requestedIds));


        if (categories.size() != requestedIds.size()) {
            throw new IllegalArgumentException("Alguna de las categorías es inexistente");
        }

        Game game = new Game();
        game.setTitle(gameRequest.getTitle());
        game.setPrice(gameRequest.getPrice());
        game.setPlatform(gameRequest.getPlatform());
        game.setCategories(categories);
        game.setImageUrl(gameRequest.getImageUrl());
        game.setStock(gameRequest.getStock());

        return gameRepository.save(game);
    }

    @SuppressWarnings("override")
    public Game editGame(Long id, GameRequest gameRequest) {


        HashSet<Long> requestedIds = new HashSet<>(gameRequest.getCategoriesIds());
        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(requestedIds));


        if (categories.size() != requestedIds.size()) {
            throw new IllegalArgumentException("Alguna de las categorías es inexistente");
        }

        return gameRepository.findById(id).map(game -> {
            game.setTitle(gameRequest.getTitle());
            game.setPrice(gameRequest.getPrice());
            game.setPlatform(gameRequest.getPlatform());
            game.setCategories(categories);
            game.setImageUrl(gameRequest.getImageUrl());
            game.setStock(gameRequest.getStock());

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
