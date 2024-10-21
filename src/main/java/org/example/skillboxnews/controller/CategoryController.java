package org.example.skillboxnews.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.controller.request.CategoryRequest;
import org.example.skillboxnews.controller.response.CategoryResponse;
import org.example.skillboxnews.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @GetMapping
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/{id}")
    public CategoryResponse updateCategoryById(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        return categoryService.update(id, categoryRequest);
    }
}
