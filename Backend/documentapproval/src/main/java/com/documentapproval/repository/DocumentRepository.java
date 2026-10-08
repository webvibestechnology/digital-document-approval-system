package com.documentapproval.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.entity.User;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByUploadedBy(User user);
    
    List<Document> findByStatus(String status);
    
    long countByStatus(DocumentStatus pending);
}
