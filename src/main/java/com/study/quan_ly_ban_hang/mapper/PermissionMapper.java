package com.study.quan_ly_ban_hang.mapper;

import com.study.quan_ly_ban_hang.dto.request.PermissionRequest;
import com.study.quan_ly_ban_hang.dto.response.PermissionResponse;
import com.study.quan_ly_ban_hang.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
