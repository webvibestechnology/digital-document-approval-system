package com.documentapproval.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.documentapproval.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> { }
