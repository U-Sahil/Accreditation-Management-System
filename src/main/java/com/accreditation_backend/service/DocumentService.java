package com.accreditation_backend.service;

import com.accreditation_backend.entity.Criterion;
import com.accreditation_backend.entity.Department;
import com.accreditation_backend.entity.Document;
import com.accreditation_backend.entity.SubCriterion;
import com.accreditation_backend.entity.User;
import com.accreditation_backend.repository.CriterionRepository;
import com.accreditation_backend.repository.DepartmentRepository;
import com.accreditation_backend.repository.DocumentRepository;
import com.accreditation_backend.repository.SubCriterionRepository;
import com.accreditation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CriterionRepository criterionRepository;
    private final SubCriterionRepository subCriterionRepository;
    private final S3Service s3Service;

    public DocumentService(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            CriterionRepository criterionRepository,
            SubCriterionRepository subCriterionRepository,
            S3Service s3Service) {

        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.criterionRepository = criterionRepository;
        this.subCriterionRepository = subCriterionRepository;
        this.s3Service = s3Service;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document createDocument(Document document) {

        if (document.getUploadedAt() == null) {
            document.setUploadedAt(LocalDateTime.now());
        }

        if (document.getStatus() == null) {
            document.setStatus("PENDING");
        }

        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    // Upload file + save document metadata
    public Document uploadDocument(
            MultipartFile file,
            String title,
            String academicYear,
            Long userId,
            Long departmentId,
            Long criterionId,
            Long subCriterionId) throws IOException {

        // 1. Upload actual file to S3
        String s3Key = s3Service.uploadFile(file);

        // 2. Create Document object
        Document document = new Document();

        document.setTitle(title);
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setAcademicYear(academicYear);
        document.setUploadedAt(LocalDateTime.now());
        document.setStatus("PENDING");

        // 3. Connect existing database records
        User user = userRepository.getReferenceById(userId);
        Department department = departmentRepository.getReferenceById(departmentId);
        Criterion criterion = criterionRepository.getReferenceById(criterionId);
        SubCriterion subCriterion =
                subCriterionRepository.getReferenceById(subCriterionId);

        document.setUploadedBy(user);
        document.setDepartment(department);
        document.setCriterion(criterion);
        document.setSubCriterion(subCriterion);

        // 4. Save S3 location in PostgreSQL
        document.setS3Key(s3Key);

        // 5. Save document metadata
        return documentRepository.save(document);
    }
}