package com.study.quan_ly_ban_hang.configuration;

import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.enums.Role;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initApplicationRunner() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                userRepository.save(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin-phusang"))
                        .roles(roles)
                        .build());
                log.warn("User admin created successfully");
            }
        };
    }
}
