package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.aop.access.EntityType;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.repository.CommentRepository;
import org.example.skillboxnews.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckAccessService {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public boolean accessible(EntityType entityType, Long id, String token) {
        if (entityType == EntityType.NEWS) {
            if (!newsRepository.existsById(id)) {
                throw new NotFoundException("News with id " + id + " not found");
            }
            return newsRepository.existsByIdAndUserToken(id, token);
        } else if (entityType == EntityType.COMMENT) {
            if (!commentRepository.existsById(id)) {
                throw new NotFoundException("Comment with id " + id + " not found");
            }
            return commentRepository.existsByIdAndUserToken(id, token);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this entity type: " + entityType);
        }
    }
}
