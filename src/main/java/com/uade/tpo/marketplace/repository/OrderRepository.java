package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);
}
