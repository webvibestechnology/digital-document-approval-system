package com.documentapproval.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentapproval.dto.DocumentRequest;
import com.documentapproval.dto.DocumentResponse;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.entity.User;
import com.documentapproval.exception.ResourceNotFoundException;
import com.documentapproval.repository.DocumentRepository;
import com.documentapproval.repository.UserRepository;
import com.documentapproval.util.FileUploadUtil;

@Service
public class DocumentService {

    @Autowired private DocumentRepository documentRepository;
    @Autowired private UserRepository userRepository;

    @Value("${file.upload.dir}")
    private String uploadDir;

    private DocumentResponse toDocumentResponse(Document doc) {
        DocumentResponse response = new DocumentResponse();
        response.setId(doc.getId());
        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setStatus(doc.getStatus().name());
        response.setUploadedByUsername(doc.getUploadedBy() != null ? doc.getUploadedBy().getUsername() : "");
        response.setFilePath(doc.getFilePath());
        response.setCreatedAt(doc.getCreatedAt() != null ? doc.getCreatedAt().toString() : "");
        return response;
    }

    public DocumentResponse uploadDocument(DocumentRequest request, MultipartFile file, String uploaderEmail) throws IOException {
        User uploader = userRepository.findByEmail(uploaderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + uploaderEmail));

        String filePath = FileUploadUtil.saveFile(uploadDir, file.getOriginalFilename(), file);

        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setDescription(request.getDescription());
        document.setFilePath(filePath);
        document.setFileType(file.getContentType());
        document.setStatus(DocumentStatus.PENDING);
        document.setUploadedBy(uploader);

        return toDocumentResponse(documentRepository.save(document));
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(this::toDocumentResponse)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return documentRepository.findByUploadedBy(user).stream()
                .map(this::toDocumentResponse)
                .collect(Collectors.toList());
    }

    public DocumentResponse getDocumentById(Long id) {
        return toDocumentResponse(documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id)));
    }

    public List<DocumentResponse> getDocumentsByStatus(String status) {
        return documentRepository.findByStatus(DocumentStatus.valueOf(status.toUpperCase())).stream()
                .map(this::toDocumentResponse)
                .collect(Collectors.toList());
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with id: " + id);
        }
        documentRepository.deleteById(id);
    }
}
