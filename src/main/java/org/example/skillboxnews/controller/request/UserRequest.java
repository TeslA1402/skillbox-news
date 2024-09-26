package org.example.skillboxnews.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.example.skillboxnews.entity.RoleType;

import java.util.List;

public record UserRequest(@Size(min = 1, max = 255) @NotBlank String login, @NotBlank String password,
                          @NotEmpty List<RoleType> roles) {
}