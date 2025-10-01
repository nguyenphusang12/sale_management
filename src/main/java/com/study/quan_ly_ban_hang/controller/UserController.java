package com.study.quan_ly_ban_hang.controller;

import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserCreationRequest req) {
        return userService.createUser(req);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
