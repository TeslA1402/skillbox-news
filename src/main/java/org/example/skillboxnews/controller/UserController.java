package org.example.skillboxnews.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.skillboxnews.aop.access.CheckAccess;
import org.example.skillboxnews.aop.access.EntityType;
import org.example.skillboxnews.controller.request.UserRequest;
import org.example.skillboxnews.controller.response.UserResponse;
import org.example.skillboxnews.service.UserService;
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
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @SecurityRequirement(name = "basicAuth")
    @CheckAccess(entityType = EntityType.USER)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @CheckAccess(entityType = EntityType.USER)
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    @CheckAccess(entityType = EntityType.USER)
    @PutMapping("/{id}")
    public UserResponse updateUserById(@Valid @RequestBody UserRequest userRequest, @PathVariable Long id) {
        return userService.update(userRequest, id);
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }
}
