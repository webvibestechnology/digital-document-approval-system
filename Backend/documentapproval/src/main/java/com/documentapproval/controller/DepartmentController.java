package com.documentapproval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.dto.DepartmentRequest; 
import com.documentapproval.entity.Department;
import com.documentapproval.exception.ResourceNotFoundException; 
import com.documentapproval.repository.DepartmentRepository;

@Service
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository; 
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, DepartmentRequest request) 
    {     
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        
        existingDepartment.setName(request.getName());
        
        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        
        departmentRepository.delete(department);
    }
}

