package com.study.quan_ly_ban_hang.configuration;

import com.study.quan_ly_ban_hang.entity.Permission;
import com.study.quan_ly_ban_hang.entity.Role;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.enums.PermissionInit;
import com.study.quan_ly_ban_hang.enums.RoleInit;
import com.study.quan_ly_ban_hang.exception.AppException;
import com.study.quan_ly_ban_hang.exception.ErrorCode;
import com.study.quan_ly_ban_hang.repository.PermissionRepository;
import com.study.quan_ly_ban_hang.repository.RoleRepository;
import com.study.quan_ly_ban_hang.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;

    public void initPermission() {
        PermissionInit[] PERMISSION_INIT = PermissionInit.values();
        for (PermissionInit permission : PERMISSION_INIT) {
            if (!permissionRepository.existsById(permission.getName())) {
                permissionRepository.save(Permission.builder()
                        .name(permission.getName())
                        .description(permission.getDescription())
                        .build());
            }
        }
    }

    public void initRole() {
        RoleInit[] ROLE_INIT = RoleInit.values();
        for (RoleInit role : ROLE_INIT) {
            if (!roleRepository.existsById(role.getName())) {
                var perms = permissionRepository.findAllById(role.getPermissions());
                roleRepository.save(Role.builder()
                        .name(role.getName())
                        .description(role.getDescription())
                        .permissions(new HashSet<>(perms))
                        .build());
            }
        }
    }

    public void initUserAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin-phusang"))
                    .roles(Set.of(roleRepository.
                            findById(RoleInit.ADMIN.getName())
                            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                    )
                    .build());
            log.warn("User admin created successfully");
        }
    }

    public void initData() {
        initPermission();
        initRole();
        initUserAdmin();
    }

    @Bean
    public ApplicationRunner initApplicationRunner() {
        return args -> {
            initData();

        };
    }
}
