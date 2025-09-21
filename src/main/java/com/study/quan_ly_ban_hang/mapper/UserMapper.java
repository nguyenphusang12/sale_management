package com.study.quan_ly_ban_hang.mapper;


import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
