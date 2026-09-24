package com.documentapproval.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.documentapproval.dto.DepartmentRequest; // रिक्वेस्ट डेटासाठी DTO
import com.documentapproval.entity.Department;
import com.documentapproval.exception.ResourceNotFoundException; // कस्टम एक्सेप्शन क्लास
import com.documentapproval.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository; // DepartmentRepository Inject केली आहे

    // 1. सर्व डिपार्टमेंट्सची लिस्ट मिळवणे
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // 2. आयडीवरून (ID) विशिष्ट डिपार्टमेंट शोधणे (नसल्यास एरर देणे)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    // 3. नवीन डिपार्टमेंट तयार करणे (Create)
    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        // रिक्वेस्ट फाईल मधून डेटा मॉडेल क्लासमध्ये सेट करा
        department.setName(request.getName()); // तुमच्या व्हेरीएबल्स नुसार नाव बदला
        // department.setDescription(request.getDescription()); // आवश्यक असल्यास
        
        return departmentRepository.save(department);
    }

    // 4. जुने डिपार्टमेंट अपडेट करणे (Update)
    public Department updateDepartment(Long id, DepartmentRequest request) {
        // आधी तो डिपार्टमेंट डेटाबेसमध्ये अस्तित्वात आहे का ते तपासा
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        
        // नवीन डेटा अपडेट करा
        existingDepartment.setName(request.getName());
        // existingDepartment.setDescription(request.getDescription());
        
        return departmentRepository.save(existingDepartment);
    }

    // 5. डिपार्टमेंट डिलीट करणे (Delete)
    public void deleteDepartment(Long id) {
        // आधी तो डिपार्टमेंट डेटाबेसमध्ये आहे का ते तपासा
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        
        departmentRepository.delete(department);
    }
}

