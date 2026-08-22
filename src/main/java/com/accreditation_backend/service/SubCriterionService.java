package com.accreditation_backend.service;

import com.accreditation_backend.entity.SubCriterion;
import com.accreditation_backend.repository.SubCriterionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCriterionService {

    private final SubCriterionRepository subCriterionRepository;

    public SubCriterionService(SubCriterionRepository subCriterionRepository) {
        this.subCriterionRepository = subCriterionRepository;
    }

    public List<SubCriterion> getAllSubCriteria() {
        return subCriterionRepository.findAll();
    }

    public SubCriterion createSubCriterion(SubCriterion subCriterion) {
        return subCriterionRepository.save(subCriterion);
    }
}