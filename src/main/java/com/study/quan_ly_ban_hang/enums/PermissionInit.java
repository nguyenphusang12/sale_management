package com.study.quan_ly_ban_hang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissionInit {

    ADMIN("PERMISSION_ADMIN", "permission for admin"),
    USER("PERMISSION_USER", "permission for user");

    private final String name;
    private final String description;
}
