package com.documentapproval.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.documentapproval.dto.UserRequest;
import com.documentapproval.dto.UserResponse;
import com.documentapproval.dto.repository.UserRepository;
import com.documentapproval.entity.User;
import com.documentapproval.exception.ResourceNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository; 

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoleName().getName()))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = ((Object) userRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return new UserResponse(((UserResponse) user).getId(), user.getUsername(), user.getEmail(), user.getRoleName().getName());
    }
    
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = ((Object) userRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getRoleName().getName());
    }

    public void deleteUser(Long id) {
        User user = ((Object) userRepository.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}

