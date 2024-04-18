package org.example.skillboxnews.repository;

import org.example.skillboxnews.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends JpaRepository<News, Long>, PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News> {
    boolean existsByIdAndUserToken(Long id, String token);
}