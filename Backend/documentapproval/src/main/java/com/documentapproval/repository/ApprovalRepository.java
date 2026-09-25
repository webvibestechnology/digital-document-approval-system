package com.documentapproval.repository;

import com.documentapproval.entity.Approval;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByApprover(User approver);
    List<Approval> findByDocument(Document document);
    Optional<Approval> findByDocumentAndApprover(Document doc, User approver);
}
