package org.example.skillboxnews.mapper;

import org.example.skillboxnews.entity.Role;
import org.example.skillboxnews.entity.RoleType;
import org.example.skillboxnews.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Role toRole(User user, RoleType role);
}
