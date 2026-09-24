package com.documentapproval.repository;

	import java.time.LocalDateTime;
import java.util.List;
	import java.util.Optional;
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;
	import com.documentapproval.entity.Approval;
	import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.entity.User;

	@Repository
	public interface ApprovalRepository extends JpaRepository<Approval, Long> {
	    
	    List<Approval> findByDocument(Document document);
	    
	    List<Approval> findByApprover(User approver);
	    
	    Optional<Approval> findByDocumentAndApprover(Document doc, User approver);

		long countByStatus(String string);

		Optional<Document> findByDocumentIdAndApproverEmail(Object documentId, String approverEmail);

		Approval save(Document approval);

		List<Approval> findByApproverEmailAndStatus(String approverEmail, DocumentStatus pending);

		List<Approval> findByDocumentId(Long documentId);

		long countByCreatedAtAfter(LocalDateTime startOfMonth);
	}


