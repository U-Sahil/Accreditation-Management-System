package com.accreditation_backend.service;

import com.accreditation_backend.entity.Criterion;
import com.accreditation_backend.repository.CriterionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriterionService {

    private final CriterionRepository criterionRepository;

    public CriterionService(CriterionRepository criterionRepository) {
        this.criterionRepository = criterionRepository;
    }

    public List<Criterion> getAllCriteria() {
        return criterionRepository.findAll();
    }

    public Criterion createCriterion(Criterion criterion) {
        return criterionRepository.save(criterion);
    }
}