package com.documentapproval.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.documentapproval.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
