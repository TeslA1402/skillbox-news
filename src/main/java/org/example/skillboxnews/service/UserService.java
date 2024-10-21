package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.controller.request.UserRequest;
import org.example.skillboxnews.controller.response.UserResponse;
import org.example.skillboxnews.entity.Role;
import org.example.skillboxnews.entity.User;
import org.example.skillboxnews.exception.AlreadyExistsException;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.mapper.UserMapper;
import org.example.skillboxnews.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Creating user {} with roles {}", userRequest.login(), userRequest.roles());
        if (userRepository.existsByLoginIgnoreCase(userRequest.login())) {
            throw new AlreadyExistsException("User with login " + userRequest.login() + " already exists");
        }
        User user = userRepository.save(userMapper.toUser(userRequest, passwordEncoder.encode(userRequest.password())));
        userRequest.roles().stream().map(roleType -> roleService.save(user, roleType)).forEach(user::addRole);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user by id: {}", userId);
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        log.info("Retrieving user by id: {}", userId);
        User user = getUser(userId);
        return userMapper.toUserResponse(user);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public Page<UserResponse> getUsers(Pageable pageable) {
        log.info("Retrieving all users: {}", pageable);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponse);
    }

    @Transactional
    public UserResponse update(UserRequest userRequest, Long id) {
        log.info("Update user: {}", userRequest.login());
        User user = getUser(id);
        if (!user.getLogin().equals(userRequest.login()) && userRepository.existsByLoginIgnoreCase(userRequest.login())) {
            throw new AlreadyExistsException("User with login " + userRequest.login() + " already exists");
        }

        Set<Role> roles = userRequest.roles().stream().map(roleType -> roleService.save(user, roleType)).collect(Collectors.toSet());
        roles.forEach(user::addRole);
        Set<Role> forDelete = user.getRoles().stream().filter(role -> !roles.contains(role)).collect(Collectors.toSet());
        forDelete.forEach(user::removeRole);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setLogin(userRequest.login());
        return userMapper.toUserResponse(user);
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
