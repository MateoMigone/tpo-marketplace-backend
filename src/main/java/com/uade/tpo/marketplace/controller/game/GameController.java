/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.uade.tpo.marketplace.controller.game;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.uade.tpo.marketplace.exceptions.InvalidDiscountException;
import com.uade.tpo.marketplace.exceptions.NegativePriceException;
import com.uade.tpo.marketplace.exceptions.NegativeStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id)  {
        Game game = gameService.getGameById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/admin/create-with-image")
    public ResponseEntity<?> createGameWithImage(
            @RequestParam("title") String title,
            @RequestParam("price") Double price,
            @RequestParam("stock") Integer stock,
            @RequestParam("platform") String platform,
            @RequestParam("categoriesIds") List<Long> categoriesIds,
            @RequestParam("imagen") MultipartFile imagen
    ) {
        try {
            // 1️⃣ Guardar imagen en carpeta local
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String originalName = imagen.getOriginalFilename();
            // Reemplazar caracteres peligrosos por guión bajo y evitar espacios en blanco
            String safeOriginal = (originalName == null) ? "file" : originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = System.currentTimeMillis() + "_" + safeOriginal;
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, imagen.getBytes());

            // 2️⃣ Crear URL pública (URL-encode filename para que espacios y caracteres especiales no rompan la URL)
            String encoded = java.net.URLEncoder.encode(fileName, java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
            String imagenUrl = "http://localhost:4002/uploads/" + encoded;

            // 3️⃣ Crear GameRequest
            GameRequest gameRequest = new GameRequest();
            gameRequest.setTitle(title);
            gameRequest.setPrice(price);
            gameRequest.setStock(stock);
            gameRequest.setPlatform(platform);
            // Asegurar un valor por defecto para discount si no fue enviado
            if (gameRequest.getDiscount() == null) {
                gameRequest.setDiscount(0.0);
            }
            gameRequest.setImageUrl(imagenUrl);
            gameRequest.setCategoriesIds(categoriesIds);

            // 4️⃣ Llamar al servicio existente
            Game result = gameService.createGame(gameRequest);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear el videojuego: " + e.getMessage());
        }
    }

    @PutMapping(value = "/admin/{id}/edit-with-image", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editGameWithImage(
        @PathVariable Long id,
        @org.springframework.web.bind.annotation.RequestPart("game") String gameJson,
        @org.springframework.web.bind.annotation.RequestPart("image") MultipartFile image
    ) {
        try {
            // Guardar imagen en carpeta local (mismo comportamiento que create-with-image)
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String originalName = image.getOriginalFilename();
            String safeOriginal = (originalName == null) ? "file" : originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = System.currentTimeMillis() + "_" + safeOriginal;
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, image.getBytes());

            String encoded = java.net.URLEncoder.encode(fileName, java.nio.charset.StandardCharsets.UTF_8.toString()).replace("+", "%20");
            String imageUrl = "http://localhost:4002/uploads/" + encoded;

            // Parsear gameJson a GameRequest
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            GameRequest gameRequest = mapper.readValue(gameJson, GameRequest.class);
            // Asegurar un valor por defecto para discount si no fue enviado
            if (gameRequest.getDiscount() == null) {
                gameRequest.setDiscount(0.0);
            }
            gameRequest.setImageUrl(imageUrl);

            Game updated = gameService.editGame(id, gameRequest);
            if (updated != null) return ResponseEntity.ok(updated);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al editar el videojuego: " + e.getMessage());
        }
    }

}
