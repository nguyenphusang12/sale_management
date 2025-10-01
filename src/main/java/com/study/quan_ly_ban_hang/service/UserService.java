package com.study.quan_ly_ban_hang.service;

import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.dto.request.UserUpdateRequest;
import com.study.quan_ly_ban_hang.entity.User;
import com.study.quan_ly_ban_hang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest req) {
        User user = new User();

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User updateUser(String id, UserUpdateRequest req) {
        User user = getUserById(id);

        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());

        return userRepository.save(user);
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been deleted";
    }
}
