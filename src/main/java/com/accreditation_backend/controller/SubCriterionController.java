package com.accreditation_backend.controller;

import com.accreditation_backend.entity.SubCriterion;
import com.accreditation_backend.service.SubCriterionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-criteria")
@CrossOrigin(origins = "http://localhost:5173")
public class SubCriterionController {

    private final SubCriterionService subCriterionService;

    public SubCriterionController(SubCriterionService subCriterionService) {
        this.subCriterionService = subCriterionService;
    }

    @GetMapping
    public List<SubCriterion> getAllSubCriteria() {
        return subCriterionService.getAllSubCriteria();
    }

    @PostMapping
    public SubCriterion createSubCriterion(@RequestBody SubCriterion subCriterion) {
        return subCriterionService.createSubCriterion(subCriterion);
    }
}