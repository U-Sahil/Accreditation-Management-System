package com.accreditation_backend.service;

import com.accreditation_backend.entity.Approval;
import com.accreditation_backend.entity.Document;
import com.accreditation_backend.entity.User;
import com.accreditation_backend.repository.ApprovalRepository;
import com.accreditation_backend.repository.DocumentRepository;
import com.accreditation_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public ApprovalService(
            ApprovalRepository approvalRepository,
            DocumentRepository documentRepository,
            UserRepository userRepository) {

        this.approvalRepository = approvalRepository;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public Approval reviewDocument(
            Long documentId,
            Long reviewerId,
            String status,
            String comments) {

        // Find document
        Document document = documentRepository
                .findById(documentId)
                .orElseThrow(() ->
                        new RuntimeException("Document not found"));

        // Find reviewer
        User reviewer = userRepository
                .findById(reviewerId)
                .orElseThrow(() ->
                        new RuntimeException("Reviewer not found"));

        // Create approval record
        Approval approval = new Approval();

        approval.setDocument(document);
        approval.setReviewer(reviewer);
        approval.setStatus(status);
        approval.setComments(comments);
        approval.setReviewedAt(LocalDateTime.now());

        // Update document status
        document.setStatus(status);

        documentRepository.save(document);

        return approvalRepository.save(approval);
    }

    public List<Approval> getApprovalsForDocument(Long documentId) {
        return approvalRepository.findByDocumentId(documentId);
    }

    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }
}