package com.documentapproval.dto;

import lombok.Data;

@Data
public class LoginRequest{
	private String Email;
	private String Password;
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}}

	
	 