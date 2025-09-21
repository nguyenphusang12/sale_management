package com.study.quan_ly_ban_hang.dto.response;

import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Instant createdAt;
}