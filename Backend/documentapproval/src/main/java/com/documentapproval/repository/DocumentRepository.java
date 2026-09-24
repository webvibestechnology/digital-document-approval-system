package com.documentapproval.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByUploadedBy(User user);
    
    List<Document> findByStatus(DocumentStatus status);

	Object findByEmail(String email);

	Optional<Document> findByUserEmail(String email);

	Long countByStatus(String string);
}
