package org.example.skillboxnews.mapper;

import org.example.skillboxnews.controller.request.NewsRequest;
import org.example.skillboxnews.controller.response.NewsResponse;
import org.example.skillboxnews.entity.Category;
import org.example.skillboxnews.entity.News;
import org.example.skillboxnews.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {CategoryMapper.class, CommentMapper.class})
public interface NewsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    News toNews(NewsRequest newsRequest, User user, Category category);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "commentCount", expression = "java(news.getComments().size())")
    NewsResponse toNewsResponse(News news);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "commentCount", expression = "java(news.getComments().size())")
    NewsResponse toNewsResponseWithoutComments(News news);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(NewsRequest newsRequest, Category category, @MappingTarget News news);
}