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

    // 1. नवीन डॉक्युमेंट अपलोड करणे (Upload Document)
    @SuppressWarnings("static-access")
	public DocumentResponse uploadDocument(DocumentRequest request, MultipartFile file, String uploaderEmail) throws IOException {
        
        // A. ईमेलवरून अपलोड करणाऱ्या युजरला शोधणे
        Document user = userRepository.findByEmail(uploaderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + uploaderEmail));

        // B. FileUploadUtil.saveFile() वापरून फाईल डिस्क (Hard Disk) वर सेव्ह करणे
        String fileName = file.getOriginalFilename();
        fileUploadUtil.saveFile(uploadDir, fileName, file); // तुमच्या utility मेथडच्या पॅरामीटर्सनुसार बदल करा

        // C. Document Entity तयार करून स्टेटस PENDING सेट करणे
        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setFilePath(uploadDir + fileName);
        document.setStatus(DocumentStatus.PENDING); // किंवा "PENDING" जर String असेल तर
        document.setUser(user);

        // D. डेटाबेसमध्ये सेव्ह करणे
        Document savedDocument = documentRepository.save(document);

        // E. DocumentResponse मध्ये रूपांतर करून रिटर्न करणे
        return mapToResponse(savedDocument);
    }

    // 2. सर्व डॉक्युमेंट्सची लिस्ट मिळवणे (फक्त ADMIN साठी)
    public List<DocumentResponse> getAllDocuments() {
        return documentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 3. लॉग-इन असलेल्या युजरचे स्वतःचे डॉक्युमेंट्स मिळवणे
    public List<DocumentResponse> getDocumentsByUser(String email) {
        return documentRepository.findByUserEmail(email) // तुमच्या Repository मधील मेथडचे नाव तपासा
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 4. आयडीवरून (ID) विशिष्ट डॉक्युमेंट शोधणे
    public DocumentResponse getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        return mapToResponse(document);
    }

    // 5. स्टेटसवरून (PENDING, APPROVED इ.) डॉक्युमेंट्स फिल्टर करणे
    public List<DocumentResponse> getDocumentsByStatus(DocumentStatus status) {
        return documentRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 6. डॉक्युमेंट डिलीट करणे
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
        documentRepository.delete(document);
    }

    // Entity चे रूपांतर Response DTO मध्ये करण्यासाठी हेल्पर मेथड
    private DocumentResponse mapToResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setTitle(document.getTitle());
        response.setFilePath(document.getFilePath());
        response.setStatus(document.getStatus());
        response.setUploaderName(document.getUser().getName()); // युजरचे नाव
        return response;
    }
}
