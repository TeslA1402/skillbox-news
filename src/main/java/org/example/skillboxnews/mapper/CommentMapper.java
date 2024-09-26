package org.example.skillboxnews.mapper;

import org.example.skillboxnews.controller.request.CommentRequest;
import org.example.skillboxnews.controller.response.CommentResponse;
import org.example.skillboxnews.entity.Comment;
import org.example.skillboxnews.entity.News;
import org.example.skillboxnews.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "commentRequest.text", target = "text")
    @Mapping(source = "news", target = "news")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", source = "user")
    Comment toComment(CommentRequest commentRequest, User user, News news);

    @Mapping(source = "news.id", target = "newsId")
    @Mapping(source = "user.id", target = "userId")
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "news", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(CommentRequest commentRequest, @MappingTarget Comment comment);

}