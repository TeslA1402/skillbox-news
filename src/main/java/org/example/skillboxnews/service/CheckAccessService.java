package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.aop.access.Action;
import org.example.skillboxnews.aop.access.CheckAccess;
import org.example.skillboxnews.aop.access.EntityType;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.repository.CommentRepository;
import org.example.skillboxnews.repository.NewsRepository;
import org.example.skillboxnews.security.AppUserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckAccessService {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    private static boolean isRoleNotUser(AppUserPrincipal principal) {
        return principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .noneMatch(role -> role.equals("ROLE_USER"));
    }

    public boolean accessible(CheckAccess checkAccess, Long id, AppUserPrincipal principal) {
        EntityType entityType = checkAccess.entityType();
        Action action = checkAccess.action();
        switch (entityType) {
            case EntityType.NEWS -> {
                if (!newsRepository.existsById(id)) {
                    throw new NotFoundException("News with id " + id + " not found");
                }
                if (action == Action.DELETE) {
                    return isRoleNotUser(principal) || newsRepository.existsByIdAndUser(id, principal.user());
                } else {
                    return newsRepository.existsByIdAndUser(id, principal.user());
                }
            }
            case EntityType.COMMENT -> {
                if (!commentRepository.existsById(id)) {
                    throw new NotFoundException("Comment with id " + id + " not found");
                }
                if (action == Action.DELETE) {
                    return isRoleNotUser(principal) || commentRepository.existsByIdAndUser(id, principal.user());
                } else {
                    return commentRepository.existsByIdAndUser(id, principal.user());
                }
            }
            case USER -> {
                return isRoleNotUser(principal) || principal.user().getId().equals(id);
            }
            default ->
                    throw new UnsupportedOperationException("Operation not supported for this entity type: " + entityType);
        }
    }
}
