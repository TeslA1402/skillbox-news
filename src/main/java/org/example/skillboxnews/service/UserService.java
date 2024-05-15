package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.controller.request.UserRequest;
import org.example.skillboxnews.controller.response.UserResponse;
import org.example.skillboxnews.controller.response.UserWithTokenResponse;
import org.example.skillboxnews.entity.User;
import org.example.skillboxnews.exception.AlreadyExistsException;
import org.example.skillboxnews.exception.NotFoundException;
import org.example.skillboxnews.mapper.UserMapper;
import org.example.skillboxnews.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserWithTokenResponse createUser(UserRequest userRequest) {
        log.info("Creating user: {}", userRequest);
        String token = UUID.randomUUID().toString();
        if (userRepository.existsByLoginIgnoreCase(userRequest.login())) {
            throw new AlreadyExistsException("User with login " + userRequest.login() + " already exists");
        }
        User user = userRepository.save(userMapper.toUser(userRequest, token));
        return userMapper.toUserWithTokenResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user by id: {}", userId);
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public User getByToken(String token) {
        return userRepository.findByToken(token).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        log.info("Retrieving user by id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public Page<UserResponse> getUsers(Pageable pageable) {
        log.info("Retrieving all users: {}", pageable);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponse);
    }
}
