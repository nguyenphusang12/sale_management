package com.study.quan_ly_ban_hang.mapper;

import com.study.quan_ly_ban_hang.dto.request.UserCreationRequest;
import com.study.quan_ly_ban_hang.dto.request.UserUpdateRequest;
import com.study.quan_ly_ban_hang.dto.response.UserResponse;
import com.study.quan_ly_ban_hang.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}
