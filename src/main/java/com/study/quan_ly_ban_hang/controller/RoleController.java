package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.ApiResponse;
import com.study.quan_ly_ban_hang.dto.request.RoleRequest;
import com.study.quan_ly_ban_hang.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse create(@RequestBody RoleRequest req) {
        return ApiResponse.builder()
                .data(roleService.createRole(req))
                .build();
    }

    @GetMapping
    ApiResponse getAll() {
        return ApiResponse.builder()
                .data(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse delete(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.builder().build();
    }
}
