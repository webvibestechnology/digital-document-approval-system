package com.documentapproval.repository;

import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUploadedBy(User user);
    List<Document> findByStatus(DocumentStatus status);
}
