/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.controller.game;

import java.util.List;

import com.uade.tpo.marketplace.exceptions.InvalidDiscountException;
import com.uade.tpo.marketplace.exceptions.NegativePriceException;
import com.uade.tpo.marketplace.exceptions.NegativeStockException;
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

    @PostMapping("/admin/create")
    public ResponseEntity<Game> createGame(@RequestBody GameRequest gameRequest) throws NegativeStockException, InvalidDiscountException, NegativePriceException {
        Game result = gameService.createGame(gameRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Game> editGame(@PathVariable Long id, @RequestBody GameRequest gameRequest) throws NegativeStockException, InvalidDiscountException, NegativePriceException {
        Game updatedGame = gameService.editGame(id, gameRequest);
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/get/available")
    public ResponseEntity<List<Game>> getAllAvailableGames() {
        List<Game> games = gameService.getAllAvailableGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/get/category")
    public ResponseEntity<List<Game>> getGamesByCategory(@RequestParam String name) {
        List<Game> games = gameService.getGamesByCategory(name);
        return ResponseEntity.ok(games);
    }

    // Buscar por rango de precios
    // Ej: GET /api/v1/games/precio?min=20&max=80
    @GetMapping("/get/price")
    public ResponseEntity<List<Game>> getGamesByPrice(
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {

        if (min != null && max != null) {
            return ResponseEntity.ok(gameService.findByRangePrice(min, max));
        } else if (max != null) {
            return ResponseEntity.ok(gameService.findByPriceMax(max));
        } else if (min != null) {
            return ResponseEntity.ok(gameService.findByPriceMin(min));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Buscar juegos por nombre (ej: "Zelda" o "Call of Duty")
    // Ejemplo: GET /api/v1/games/nombre?nombre=Zelda
    @GetMapping("/get/title")
    public ResponseEntity<List<Game>> getGamesByTitle(@RequestParam String title) {
        return ResponseEntity.ok(gameService.findByTitle(title));
    }

}
