package com.documentapproval.services;

import com.documentapproval.dto.LoginRequest;
import com.documentapproval.dto.RegisterRequest;
import com.documentapproval.entity.Department;
import com.documentapproval.entity.Role;
import com.documentapproval.entity.User;
import com.documentapproval.exception.BadRequestException;
import com.documentapproval.exception.ResourceNotFoundException;
import com.documentapproval.repository.DepartmentRepository;
import com.documentapproval.repository.RoleRepository;
import com.documentapproval.repository.UserRepository;
import com.documentapproval.security.CustomUserDetailsService;
import com.documentapproval.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private CustomUserDetailsService userDetailsService;

    public Map<String, String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        Optional<Role> role = roleRepository.findByName(request.getRoleName());
        if (role.isEmpty()) {
            throw new BadRequestException("Role not found: " + request.getRoleName()
                + ". Valid roles are: ADMIN, USER, APPROVER");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role.get());
        user.setCreatedAt(LocalDateTime.now());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new BadRequestException("Department not found with id: " + request.getDepartmentId()));
            user.setDepartment(department);
        }

        userRepository.save(user);
        return Map.of("message", "Registration successful!");
    }

    public Map<String, String> login(LoginRequest request) {
        // This throws BadCredentialsException if wrong — handled by GlobalExceptionHandler
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String roleName = (user.getRole() != null) ? user.getRole().getName() : "USER";
        String username = (user.getUsername() != null) ? user.getUsername() : user.getEmail();

        return Map.of(
                "token", token,
                "role", roleName,
                "username", username
        );
    }
}
