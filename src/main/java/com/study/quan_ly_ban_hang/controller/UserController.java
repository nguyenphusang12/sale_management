package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.ApiResponse;
import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.dto.request.UserUpdateRequest;
import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

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
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
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
