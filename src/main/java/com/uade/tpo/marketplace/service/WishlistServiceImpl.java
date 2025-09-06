package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Game;
import com.uade.tpo.marketplace.entity.Wishlist;
import com.uade.tpo.marketplace.repository.GameRepository;
import com.uade.tpo.marketplace.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist getWishlistById(Long id) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist no encontrada: " + id));
        return wishlist;
    }

    @Transactional
    public Wishlist addGame(Long id, Long gameId) {

        // Traemos las entidades
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + gameId));

        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist no encontrada: " + id));

        // Chequeamos que la lisat de juegos no sea null
        if (wishlist.getGameList() == null) {
            wishlist.setGameList(new HashSet<>());
        }
        // Chequeamos que no se duplique el juego si ya esta en la wishlist
        if (!wishlist.getGameList().contains(game)) {
            wishlist.getGameList().add(game);
        }

        // Guardamos la wishlist
        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public Wishlist deleteGame(Long id, Long gameId) {

        // Traemos las entidades
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado: " + gameId));

        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist no encontrada: " + id));

        // Chequeamos que la lisat de juegos no sea null
        if (wishlist.getGameList() == null) {
            wishlist.setGameList(new HashSet<>());
        }

        // Chequeamos que el juego este en la wishlist
        if (wishlist.getGameList().contains(game)) {
            wishlist.getGameList().remove(game);
        }

        // Guardamos la wishlist
        return wishlistRepository.save(wishlist);
    }
}
