package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.ApiResponse;
import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.dto.request.UserUpdateRequest;
import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse createUser(@RequestBody @Valid UserCreationRequest req) {
        ApiResponse response = new ApiResponse();
        response.setData(userService.createUser(req));
        return response;
    }

    @GetMapping
    public ApiResponse getUsers() {
        ApiResponse response = new ApiResponse();
        response.setData(userService.getUsers());
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse getUserById(@PathVariable String id) {
        ApiResponse response = new ApiResponse();
        response.setData(userService.getUserById(id));
        return response;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserUpdateRequest req) {
        return userService.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

}
