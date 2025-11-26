package com.study.quan_ly_ban_hang.repository;

import com.study.quan_ly_ban_hang.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
