package com.uade.tpo.marketplace.controller.wishlist;

import com.uade.tpo.marketplace.entity.Wishlist;
import com.uade.tpo.marketplace.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> getWishlistById(@PathVariable Long id) {
        Wishlist wishlist = wishlistService.getWishlistById(id);
        return ResponseEntity.ok(wishlist);
    }

    @PutMapping("/{id}/add")
    public ResponseEntity<Wishlist> addGame(@PathVariable Long id, @RequestBody WishlistRequest wishlistRequest) {
        Wishlist updatedWishlist = wishlistService.addGame(id, wishlistRequest.getGameId());
        if (updatedWishlist != null) {
            return ResponseEntity.ok(updatedWishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Wishlist> deleteGame(@PathVariable Long id, @RequestBody WishlistRequest wishlistRequest) {
        Wishlist updatedWishlist = wishlistService.deleteGame(id, wishlistRequest.getGameId());
        if (updatedWishlist != null) {
            return ResponseEntity.ok(updatedWishlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
