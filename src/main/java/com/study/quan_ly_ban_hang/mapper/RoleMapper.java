package com.study.quan_ly_ban_hang.mapper;

import com.study.quan_ly_ban_hang.dto.request.RoleRequest;
import com.study.quan_ly_ban_hang.dto.response.RoleResponse;
import com.study.quan_ly_ban_hang.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
