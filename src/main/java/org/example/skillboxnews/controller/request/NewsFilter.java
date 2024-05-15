package org.example.skillboxnews.controller.request;

import org.example.skillboxnews.entity.Category;
import org.example.skillboxnews.entity.News;
import org.example.skillboxnews.entity.User;
import org.springframework.data.jpa.domain.Specification;

public record NewsFilter(Long userId, Long categoryId) {

    public Specification<News> getSpecification() {
        return Specification.where(byUserId(userId))
                .and(byCategoryId(categoryId));
    }

    private Specification<News> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(News.Fields.user).get(User.Fields.id), userId);
            }
        };
    }

    private Specification<News> byCategoryId(Long categoryId) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            } else {
                return criteriaBuilder.equal(root.get(News.Fields.category).get(Category.Fields.id), categoryId);
            }
        });
    }

}
