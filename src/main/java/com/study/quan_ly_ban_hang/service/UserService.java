package com.study.quan_ly_ban_hang.service;

import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.dto.request.UserUpdateRequest;
import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.exception.AppException;
import com.study.quan_ly_ban_hang.exception.ErrorCode;
import com.study.quan_ly_ban_hang.mapper.UserMapper;
import com.study.quan_ly_ban_hang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserCreationRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(req);
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public User updateUser(String id, UserUpdateRequest req) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, req);
        return userRepository.save(user);
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been deleted";
    }
}
