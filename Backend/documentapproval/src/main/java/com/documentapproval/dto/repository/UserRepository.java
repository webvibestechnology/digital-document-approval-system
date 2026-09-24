package com.documentapproval.dto.repository;

import java.util.Collection;
import java.util.Optional;

import com.documentapproval.dto.UserResponse;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.User;

public record UserRepository() {

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long count1() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Collection<UserResponse> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}

	public Optional<Document> findByEmail(String uploaderEmail) {
		// TODO Auto-generated method stub
		return null;
	}

}
