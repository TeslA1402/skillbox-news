package org.example.skillboxnews.repository;

import org.example.skillboxnews.entity.Comment;
import org.example.skillboxnews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByIdAndUser(Long id, User user);
}