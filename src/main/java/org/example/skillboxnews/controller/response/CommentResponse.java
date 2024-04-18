package org.example.skillboxnews.controller.response;

public record CommentResponse(Long id, String text, Long userId, Long newsId) {
}