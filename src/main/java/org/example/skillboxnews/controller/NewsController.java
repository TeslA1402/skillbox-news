package org.example.skillboxnews.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.aop.access.CheckAccess;
import org.example.skillboxnews.aop.access.EntityType;
import org.example.skillboxnews.controller.request.CommentRequest;
import org.example.skillboxnews.controller.request.NewsFilter;
import org.example.skillboxnews.controller.request.NewsRequest;
import org.example.skillboxnews.controller.response.CommentResponse;
import org.example.skillboxnews.controller.response.NewsResponse;
import org.example.skillboxnews.service.CommentService;
import org.example.skillboxnews.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;

    @PostMapping
    public NewsResponse createNews(@Valid @RequestBody NewsRequest newsRequest, @RequestHeader(name = "Token") String token) {
        return newsService.save(newsRequest, token);
    }

    @GetMapping
    public Page<NewsResponse> getAllNews(Pageable pageable, NewsFilter newsFilter) {
        return newsService.findAll(pageable, newsFilter);
    }

    @GetMapping("/{id}")
    public NewsResponse getNewsById(@PathVariable("id") Long id) {
        return newsService.getById(id);
    }

    @DeleteMapping("/{id}")
    @CheckAccess(entityType = EntityType.NEWS)
    public void deleteNewsById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @PostMapping("/{newsId}/comment")
    public CommentResponse addComment(@PathVariable Long newsId, @Valid @RequestBody CommentRequest commentRequest, @RequestHeader(name = "Token") String token) {
        return commentService.saveComment(newsId, commentRequest, token);
    }

    @PutMapping("/{id}")
    @CheckAccess(entityType = EntityType.NEWS)
    public NewsResponse updateNewsById(@Valid @RequestBody NewsRequest newsRequest, @PathVariable Long id) {
        return newsService.update(id, newsRequest);
    }
}
