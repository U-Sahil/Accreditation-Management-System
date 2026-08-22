package com.accreditation_backend.controller;

import com.accreditation_backend.entity.Approval;
import com.accreditation_backend.service.ApprovalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@CrossOrigin(origins = "http://localhost:5173")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping("/review")
    public Approval reviewDocument(
            @RequestParam Long documentId,
            @RequestParam Long reviewerId,
            @RequestParam String status,
            @RequestParam(required = false) String comments) {

        return approvalService.reviewDocument(
                documentId,
                reviewerId,
                status,
                comments
        );
    }

    @GetMapping
    public List<Approval> getAllApprovals() {
        return approvalService.getAllApprovals();
    }

    @GetMapping("/document/{documentId}")
    public List<Approval> getApprovalsForDocument(
            @PathVariable Long documentId) {

        return approvalService.getApprovalsForDocument(documentId);
    }
}