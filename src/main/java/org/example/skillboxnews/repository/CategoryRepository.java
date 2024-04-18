package org.example.skillboxnews.repository;

import org.example.skillboxnews.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {
    boolean existsByName(String name);
}