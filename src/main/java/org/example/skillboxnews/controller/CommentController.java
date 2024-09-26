package org.example.skillboxnews.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.aop.access.Action;
import org.example.skillboxnews.aop.access.CheckAccess;
import org.example.skillboxnews.aop.access.EntityType;
import org.example.skillboxnews.controller.request.CommentRequest;
import org.example.skillboxnews.controller.response.CommentResponse;
import org.example.skillboxnews.service.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @CheckAccess(entityType = EntityType.COMMENT, action = Action.DELETE)
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @CheckAccess(entityType = EntityType.COMMENT)
    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(id, commentRequest);
    }
}
