package com.accreditation_backend.controller;

import com.accreditation_backend.entity.Criterion;
import com.accreditation_backend.service.CriterionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria")
@CrossOrigin(origins = "http://localhost:5173")
public class CriterionController {

    private final CriterionService criterionService;

    public CriterionController(CriterionService criterionService) {
        this.criterionService = criterionService;
    }

    @GetMapping
    public List<Criterion> getAllCriteria() {
        return criterionService.getAllCriteria();
    }

    @PostMapping
    public Criterion createCriterion(@RequestBody Criterion criterion) {
        return criterionService.createCriterion(criterion);
    }
}