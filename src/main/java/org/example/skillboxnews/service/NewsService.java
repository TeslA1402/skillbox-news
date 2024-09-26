package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.controller.request.NewsFilter;
import org.example.skillboxnews.controller.request.NewsRequest;
import org.example.skillboxnews.controller.response.NewsResponse;
import org.example.skillboxnews.entity.Category;
import org.example.skillboxnews.entity.News;
import org.example.skillboxnews.entity.User;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.mapper.NewsMapper;
import org.example.skillboxnews.repository.CategoryRepository;
import org.example.skillboxnews.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<NewsResponse> findAll(Pageable pageable, NewsFilter newsFilter) {
        log.info("Request to get all News: {}", pageable);
        return newsRepository.findAll(newsFilter.getSpecification(), pageable).map(newsMapper::toNewsResponseWithoutComments);
    }

    @Transactional
    public NewsResponse save(NewsRequest newsRequest, String userName) {
        log.info("Request to save News: {}", newsRequest);
        Category category = categoryRepository.findById(newsRequest.categoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        User user = userService.getUserByLogin(userName);
        News news = newsRepository.save(newsMapper.toNews(newsRequest, user, category));
        return newsMapper.toNewsResponse(news);
    }

    @Transactional(readOnly = true)
    public NewsResponse getById(Long id) {
        log.info("Request to get News by id: {}", id);
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found"));
        return newsMapper.toNewsResponse(news);
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Request to delete News by id: {}", id);
        newsRepository.deleteById(id);
    }

    @Transactional
    public NewsResponse update(Long id, NewsRequest newsRequest) {
        log.info("Request to update News by id: {}. {}", id, newsRequest);
        News news = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found"));
        Category category = categoryRepository.findById(newsRequest.categoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        newsMapper.partialUpdate(newsRequest, category, news);
        return newsMapper.toNewsResponse(news);
    }
}
