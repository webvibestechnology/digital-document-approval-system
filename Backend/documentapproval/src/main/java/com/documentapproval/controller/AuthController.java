package com.documentapproval.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.documentapproval.dto.LoginRequest;
import com.documentapproval.dto.RegisterRequest;
import com.documentapproval.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService<?> authService;
	private Map<String, String> response;

    public AuthController(AuthService<?> authService) {
        this.authService = authService;
    }

    /**
     * 
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Map<String, String> response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

