package com.accreditation_backend.repository;

import com.accreditation_backend.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    List<Approval> findByDocumentId(Long documentId);
}