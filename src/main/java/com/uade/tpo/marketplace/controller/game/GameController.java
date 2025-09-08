/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.controller.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.service.GameService;

/**
 *
 * @author juanb
 */
@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameRequest gameRequest) {
        Game result = gameService.createGame(gameRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> editGame(@PathVariable Long id, @RequestBody GameRequest gameRequest) {
        Game updatedGame = gameService.editGame(id, gameRequest);
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Game>> getAllAvailableGames() {
        List<Game> games = gameService.getAllAvailableGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<Game>> getGamesByCategory(@RequestParam String name) {
        List<Game> games = gameService.getGamesByCategory(name);
        return ResponseEntity.ok(games);
    }
}
