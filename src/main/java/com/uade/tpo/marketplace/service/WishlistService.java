package com.uade.tpo.marketplace.service;


import com.uade.tpo.marketplace.entity.Wishlist;

public interface WishlistService {
    public Wishlist addGame(Long id, Long gameId);

    public Wishlist deleteGame(Long id, Long gameId);

    public Wishlist getWishlistById(Long id);

}
