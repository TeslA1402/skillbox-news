package org.example.skillboxnews.repository;

import org.example.skillboxnews.entity.Role;
import org.example.skillboxnews.entity.RoleType;
import org.example.skillboxnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleTypeAndUser(RoleType role, User user);
}