package com.uade.tpo.marketplace.repository;
import com.uade.tpo.marketplace.entity.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository <User, Long>{

    Optional<User> findByEmail(String email);
}
