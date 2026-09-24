package com.documentapproval.dto;

import org.jspecify.annotations.Nullable;

import lombok.Data;

@Data
public class RegisterRequest {
public String username;
public String email;
public String password;
public String roleNmae;
public String departmentId;
public class LoginRequest {
    
    private String username;
    private String password;

    // Default Constructor
    public LoginRequest() {
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
public Object getEmail1() {
	// TODO Auto-generated method stub
	return null;
}
public String getEmail() {
	// TODO Auto-generated method stub
	return null;
}
public Object getRoleName() {
	// TODO Auto-generated method stub
	return null;
}
public String getUsername() {
	// TODO Auto-generated method stub
	return null;
}
public @Nullable CharSequence getPassword() {
	// TODO Auto-generated method stub
	return null;
}

}

