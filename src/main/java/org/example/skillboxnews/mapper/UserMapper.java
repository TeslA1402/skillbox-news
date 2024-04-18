package org.example.skillboxnews.mapper;

import org.example.skillboxnews.controller.request.UserRequest;
import org.example.skillboxnews.controller.response.UserResponse;
import org.example.skillboxnews.controller.response.UserWithTokenResponse;
import org.example.skillboxnews.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "news", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(UserRequest userRequest, String token);

    UserResponse toUserResponse(User user);

    UserWithTokenResponse toUserWithTokenResponse(User user);
}