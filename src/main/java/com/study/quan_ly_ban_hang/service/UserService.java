package com.study.quan_ly_ban_hang.service;

import com.study.quan_ly_ban_hang.Repository.UserRepository;
import com.study.quan_ly_ban_hang.dto.request.CreateUserRequest;
import com.study.quan_ly_ban_hang.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserRequest req) {
        var user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail().toLowerCase().trim())
                .passwordHash(req.getPassword().trim())
                .build();
        return userRepository.save(user);
    }
}
