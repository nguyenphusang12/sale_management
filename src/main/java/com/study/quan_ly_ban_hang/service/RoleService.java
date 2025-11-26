package com.study.quan_ly_ban_hang.service;

import com.study.quan_ly_ban_hang.dto.request.RoleRequest;
import com.study.quan_ly_ban_hang.dto.response.RoleResponse;
import com.study.quan_ly_ban_hang.mapper.RoleMapper;
import com.study.quan_ly_ban_hang.repository.PermissionRepository;
import com.study.quan_ly_ban_hang.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}
