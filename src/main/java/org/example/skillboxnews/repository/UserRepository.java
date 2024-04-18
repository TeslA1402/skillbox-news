package org.example.skillboxnews.repository;

import org.example.skillboxnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByToken(String token);

    boolean existsByLoginIgnoreCase(String login);
}