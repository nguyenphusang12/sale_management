package com.study.quan_ly_ban_hang.service;

import com.study.quan_ly_ban_hang.dto.request.PermissionRequest;
import com.study.quan_ly_ban_hang.dto.response.PermissionResponse;
import com.study.quan_ly_ban_hang.entity.Permission;
import com.study.quan_ly_ban_hang.mapper.PermissionMapper;
import com.study.quan_ly_ban_hang.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
