package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.controller.request.CategoryRequest;
import org.example.skillboxnews.controller.response.CategoryResponse;
import org.example.skillboxnews.entity.Category;
import org.example.skillboxnews.exception.AlreadyExistsException;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.mapper.CategoryMapper;
import org.example.skillboxnews.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private void checkExistsByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new AlreadyExistsException("Category with name " + name + " already exists");
        }
    }

    @Transactional
    public CategoryResponse save(CategoryRequest categoryRequest) {
        log.info("Save category request: {}", categoryRequest);
        checkExistsByName(categoryRequest.name());
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));
        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Delete category by id: {}", id);
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        log.info("Get category by id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAll(Pageable pageable) {
        log.info("Get all categories: {}", pageable);
        return categoryRepository.findAll(pageable).map(categoryMapper::toCategoryResponse);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        log.info("Update category by id: {}. {}", id, categoryRequest);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        checkExistsByName(categoryRequest.name());
        categoryMapper.partialUpdate(categoryRequest, category);
        return categoryMapper.toCategoryResponse(category);
    }
}
