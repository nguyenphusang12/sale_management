package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.ApiResponse;
import com.study.quan_ly_ban_hang.dto.request.PermissionRequest;
import com.study.quan_ly_ban_hang.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse create(@RequestBody PermissionRequest req) {
        return ApiResponse.builder()
                .data(permissionService.create(req))
                .build();
    }

    @GetMapping
    ApiResponse getAll() {
        return ApiResponse.builder()
                .data(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.builder().build();
    }
}
