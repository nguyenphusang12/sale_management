package com.study.quan_ly_ban_hang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public enum RoleInit {
    ADMIN("ADMIN", "role for admin", Set.of(PermissionInit.ADMIN.getName())),
    USER("USER", "role for user", Set.of(PermissionInit.USER.getName()));

    private final String name;
    private final String description;
    private final Set<String> permissions;
}
