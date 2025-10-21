package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	java.util.List<com.uade.tpo.marketplace.entity.Order> findByUserIdOrderByDateDesc(Long userId);
}
