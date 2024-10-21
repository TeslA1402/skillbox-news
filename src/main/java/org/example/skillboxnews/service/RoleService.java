package org.example.skillboxnews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.skillboxnews.entity.Role;
import org.example.skillboxnews.entity.RoleType;
import org.example.skillboxnews.entity.User;
import org.example.skillboxnews.mapper.RoleMapper;
import org.example.skillboxnews.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public Role save(User user, RoleType roleType) {
        log.info("Saving role {} for user {}", roleType, user.getLogin());
        return roleRepository.findByRoleTypeAndUser(roleType, user).orElseGet(() -> {
            Role role = roleMapper.toRole(user, roleType);
            return roleRepository.save(role);
        });
    }
}
