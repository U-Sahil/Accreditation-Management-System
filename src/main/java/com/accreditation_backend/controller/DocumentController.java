package com.accreditation_backend.controller;

import com.accreditation_backend.entity.Document;
import com.accreditation_backend.service.DocumentService;
import com.accreditation_backend.service.S3Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentController {

    private final DocumentService documentService;
    private final S3Service s3Service;

    public DocumentController(
            DocumentService documentService,
            S3Service s3Service) {

        this.documentService = documentService;
        this.s3Service = s3Service;
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/s3-test")
    public String testS3() {

        if (s3Service.bucketExists()) {
            return "S3 connection successful!";
        }

        return "S3 connection failed!";
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return documentService.createDocument(document);
    }

    @DeleteMapping("/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return "Document deleted successfully";
    }

    // Upload file to AWS S3
    @PostMapping("/upload")
    public Document uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("academicYear") String academicYear,
            @RequestParam("userId") Long userId,
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("criterionId") Long criterionId,
            @RequestParam("subCriterionId") Long subCriterionId
    ) throws IOException {

        return documentService.uploadDocument(
                file,
                title,
                academicYear,
                userId,
                departmentId,
                criterionId,
                subCriterionId
        );
    }
}