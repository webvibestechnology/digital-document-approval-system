
package com.documentapproval.services;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.documentapproval.dto.LoginRequest;
import com.documentapproval.dto.RegisterRequest;
import com.documentapproval.entity.Role;
import com.documentapproval.entity.User;
import com.documentapproval.exception.BadRequestException;
import com.documentapproval.security.CustomUserDetailsService;
import com.documentapproval.security.JwtService;


@Service
public class AuthService<UserRepository> {

    public class RoleRepository {

		public Object findByName(Object roleName) {
			// TODO Auto-generated method stub
			return null;
		}

	}

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(UserRepository userRepository, 
                       BCryptPasswordEncoder passwordEncoder, 
                       JwtService jwtService, 
                       AuthenticationManager authenticationManager, 
                       RoleRepository roleRepository,
                       CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * ३. Method: register(RegisterRequest request) -> Map<String, String>
     */
    public Map<String, String> register(RegisterRequest request) {
        
        if (((Object) userRepository)).existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists!");
        }

        Role role = ((Object) roleRepository.findByName(request.getRoleName()))
                .orElseThrow(() -> new BadRequestException("Role not found!"));

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(role);
        
        ((Object) userRepository).save(user);

        // इ) रिटर्न संदेश {"message": "User registered successfully"}
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    /**
     * ४. Method: login(LoginRequest request) -> Map<String, String>
     */
    public Map<String, String> login(LoginRequest request) {
        
        // अ) authenticationManager.authenticate वापरून क्रेडेंशियल्स तपासणे
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // ब) CustomUserDetailsService द्वारे युझर माहिती लोड करणे
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        // क) jwtService.generateToken() वापरून JWT टोकन तयार करणे
        String jwtToken = jwtService.generateToken(userDetails);

        User user = ((Object) userRepository).findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("role", user.getRole().getName());
        response.put("username", user.getUsername());
        
        return response;
    }
}
