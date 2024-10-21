package org.example.skillboxnews.controller.response;

import org.example.skillboxnews.entity.RoleType;

import java.util.List;

public record UserResponse(Long id, String login, List<RoleType> roles) {
}