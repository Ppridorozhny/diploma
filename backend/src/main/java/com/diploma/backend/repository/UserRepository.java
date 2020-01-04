package com.diploma.backend.repository;

import com.diploma.backend.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1 or u.email = ?1")
    Optional<User> findByUsernameOrEmail(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
