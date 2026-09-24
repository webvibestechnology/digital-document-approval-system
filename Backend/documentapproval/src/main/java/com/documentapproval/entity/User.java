package com.documentapproval.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import org.springframework.security.authentication.AbstractAuthenticationToken;

@Entity
@Table(name = "users") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    
    private String email;
    
    private String password;

    
    @ManyToOne
    @JoinColumn(name = "role_id") 
    private Role role;

    @ManyToOne
    @JoinColumn(name = "department_id") 
    private Department department;

    @Column(name = "created_at") 
    private LocalDateTime createdAt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail1(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}

	public Object getRoleName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRole(Role role2) {
		// TODO Auto-generated method stub
		
	}

	public AbstractAuthenticationToken getRole() {
		// TODO Auto-generated method stub
		return null;
	}
}


