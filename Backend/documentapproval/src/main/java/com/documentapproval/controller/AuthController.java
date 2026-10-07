package com.documentapproval.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.documentapproval.dto.LoginRequest;
import com.documentapproval.dto.UserRequest;

import jakarta.validation.Valid;

    @CrossOrigin(origins = "http://localhost:4200") 
    @RestController
    @RequestMapping("/api/auth") 
    public class AuthController {
       
    @PostMapping("/login")
    public ResponseEntity<?> loginUser1(@Valid @RequestBody LoginRequest loginRequest) {
    	return ResponseEntity.ok("Login Successful");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        
        // १. तुमचा सध्याचा लॉगिन व्हेरिफिकेशनचा कोड इथे असेल (उदा. authenticationManager.authenticate...)
        // ...
        
        // २. रिस्पॉन्स पाठवताना साध्या टेक्स्ट ऐवजी असा JSON मॅप तयार करा:
        Map<String, String> response = new LinkedHashMap<>();
        ((Object) response).add("message", "Login Successful");
        // जर तुम्ही JWT टोकन पाठवत असाल तर: response.put("token", jwtToken);

        return ResponseEntity.ok(response); // साध्या स्ट्रिंग ऐवजी मॅप रिटर्न करा
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest registerRequest) {
    	return ResponseEntity.ok("Registration Successful");
    }
}
    
    