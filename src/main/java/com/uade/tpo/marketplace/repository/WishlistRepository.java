package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
