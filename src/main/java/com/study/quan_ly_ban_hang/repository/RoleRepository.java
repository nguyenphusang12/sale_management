package com.study.quan_ly_ban_hang.repository;

import com.study.quan_ly_ban_hang.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
