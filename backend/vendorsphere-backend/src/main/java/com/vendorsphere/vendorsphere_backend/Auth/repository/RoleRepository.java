package com.vendorsphere.vendorsphere_backend.Auth.repository;

import com.vendorsphere.vendorsphere_backend.Auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
