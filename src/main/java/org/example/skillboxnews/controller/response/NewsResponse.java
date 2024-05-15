package org.example.skillboxnews.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewsResponse(Long id, String text, Long userId, String title, CategoryResponse category,
                           List<CommentResponse> comments, Integer commentCount) {
}