/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.controller.Game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.service.GameService;

/**
 *
 * @author juanb
 */
public class GameController {
    @Autowired
    private GameService GameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameRequest gameRequest) {
        Game createdGame = GameService.createGame(
            gameRequest.getTitle(),
            gameRequest.getPrice(),
            gameRequest.getType(),
            gameRequest.getPlatform(),
            gameRequest.getCategories(),
            gameRequest.getImageUrl(),
            gameRequest.getStock()
        );
        return ResponseEntity.ok(createdGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> editGame(@PathVariable Long id, @RequestBody Game game) {
        Game updatedGame = GameService.editGame(id, game);
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        GameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> Games = GameService.getAllGames();
        return ResponseEntity.ok(Games);
    }
}
