package com.study.quan_ly_ban_hang.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private UUID id;
    private String fullName;
    private String email;
    private Instant createdAt;
}