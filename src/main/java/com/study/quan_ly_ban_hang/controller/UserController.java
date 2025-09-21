package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.CreateUserRequest;
import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.mapper.UserMapper;
import com.study.quan_ly_ban_hang.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest req){

        User user = userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponse(user));
    }
}
