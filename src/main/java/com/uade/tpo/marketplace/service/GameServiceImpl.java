package com.uade.tpo.marketplace.service;

import java.util.HashSet;
import java.util.List;

import com.uade.tpo.marketplace.controller.game.GameRequest;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.exceptions.InvalidDiscountException;
import com.uade.tpo.marketplace.exceptions.NegativePriceException;
import com.uade.tpo.marketplace.exceptions.NegativeStockException;
import com.uade.tpo.marketplace.repository.CategoryRepository;
import com.uade.tpo.marketplace.utils.InfoValidator;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Game createGame(GameRequest gameRequest) throws NegativeStockException, InvalidDiscountException, NegativePriceException {
        // Validamos que el stock sea un numero válido
        if (!InfoValidator.isValidStock(gameRequest.getStock())){
            throw new NegativeStockException();
        }

        // Validamos que el precio sea un numero válido
        if (!InfoValidator.isValidPrice(gameRequest.getPrice())){
            throw new NegativePriceException();
        }

        // Validamos que el descuento sea un numero válido
        if (!InfoValidator.isValidDiscount(gameRequest.getDiscount())){
            throw new InvalidDiscountException();
        }

        //Buscamos las categorias que se le quieren asignar al nuevo juego
        HashSet<Long> requestedIds = new HashSet<>(gameRequest.getCategoriesIds());
        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(requestedIds));

        //Validamos que existan las categorias
        if (categories.size() != requestedIds.size()) {
            throw new IllegalArgumentException("Alguna de las categorías es inexistente");
        }

        // Creamos un objeto Game y le seteamos la info del nuevo juego
        Game game = new Game();
        game.setTitle(gameRequest.getTitle());
        game.setPrice(gameRequest.getPrice());
        game.setDiscount(gameRequest.getDiscount());
        game.setPlatform(gameRequest.getPlatform());
        game.setCategories(categories);
        game.setImageUrl(gameRequest.getImageUrl());
        game.setStock(gameRequest.getStock());

        // Lo guardamos y lo devolvemos
        return gameRepository.save(game);
    }

    @Transactional
    public Game editGame(Long id, GameRequest gameRequest) throws NegativeStockException, NegativePriceException, InvalidDiscountException {
        // Validamos que el stock sea un numero válido
        if (!InfoValidator.isValidStock(gameRequest.getStock())){
            throw new NegativeStockException();
        }

        // Validamos que el precio sea un numero válido
        if (!InfoValidator.isValidPrice(gameRequest.getPrice())){
            throw new NegativePriceException();
        }

        // Validamos que el descuento sea un numero válido
        if (!InfoValidator.isValidDiscount(gameRequest.getDiscount())){
            throw new InvalidDiscountException();
        }

        //Buscamos las categorias que se le quieren asignar al juego
        HashSet<Long> requestedIds = new HashSet<>(gameRequest.getCategoriesIds());
        HashSet<Category> categories = new HashSet<>(categoryRepository.findAllById(requestedIds));

        //Validamos que existan las categorias
        if (categories.size() != requestedIds.size()) {
            throw new IllegalArgumentException("Alguna de las categorías es inexistente");
        }

        // Buscamos el juego y lo actualizamos
        return gameRepository.findById(id).map(game -> {
            game.setTitle(gameRequest.getTitle());
            game.setPrice(gameRequest.getPrice());
            game.setDiscount(gameRequest.getDiscount());
            game.setPlatform(gameRequest.getPlatform());
            game.setCategories(categories);
            game.setImageUrl(gameRequest.getImageUrl());
            game.setStock(gameRequest.getStock());

            // Guardamos y devolvemos
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

    public List<Game> getAllAvailableGames() {
        return gameRepository.findByStockGreaterThan(0);
    }

    public List<Game> getGamesByCategory(String name) {
        return gameRepository.findByCategories_Name(name);
    }

    //Override
    public List<Game> findByRangePrice(Double min, Double max) {
        return gameRepository.findByPriceBetween(min, max);
    }

    //@Override
    public List<Game> findByPriceMax(Double max) {
        return gameRepository.findByPriceLessThanEqual(max);
    }

    //@Override
    public List<Game> findByPriceMin(Double min) {
        return gameRepository.findByPriceGreaterThanEqual(min);
    }

    public List<Game> findByTitle(String title) {
        return gameRepository.findByTitleContainingIgnoreCase(title);
    }

}
