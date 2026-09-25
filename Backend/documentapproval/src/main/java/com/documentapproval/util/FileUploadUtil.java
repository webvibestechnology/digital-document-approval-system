package com.documentapproval.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

public class FileUploadUtil {
    
    public static String saveFile(String uploadDir, String originalFilename, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path destPath = uploadPath.resolve(originalFilename);
        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
        
        return destPath.toString();
    }
}
