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
import com.documentapproval.dto.repository.UserRepository;
import com.documentapproval.entity.Document;
import com.documentapproval.entity.DocumentStatus;
import com.documentapproval.exception.ResourceNotFoundException;
import com.documentapproval.repository.DocumentRepository;
import com.documentapproval.util.FileUploadUtil; // तुमच्या Utility क्लासचे पॅकेज तपासा

@Service
public class DocumentService<User> {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${file.upload.dir}")
    private String uploadDir;

    @SuppressWarnings("static-access")
	public DocumentResponse uploadDocument(DocumentRequest request, MultipartFile file, String uploaderEmail) throws IOException {
        
        Document user = userRepository.findByEmail(uploaderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + uploaderEmail));

        String fileName = file.getOriginalFilename();
        fileUploadUtil.saveFile(uploadDir, fileName, file); 

        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setFilePath(uploadDir + fileName);
        document.setStatus(DocumentStatus.PENDING); 
        document.setUser(user);

        Document savedDocument = documentRepository.save(document);

        return mapToResponse(savedDocument);
    }

    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsByUser(String email) {
        return documentRepository.findByUserEmail(email) 
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DocumentResponse getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return mapToResponse(document);
    }

    public List<DocumentResponse> getDocumentsByStatus(DocumentStatus status) {
        return documentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        documentRepository.delete(document);
    }

    private DocumentResponse mapToResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setTitle(document.getTitle());
        response.setFilePath(document.getFilePath());
        response.setStatus(document.getStatus());
        response.setUploaderName(document.getUser().getName()); 
        return response;
    }
}
