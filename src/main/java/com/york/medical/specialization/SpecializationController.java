package com.york.medical.specialization;

import com.york.medical.dto.CommonApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<SpecializationResponseDto> fetchAllSpecialization() {
        return specializationService.fetchAllSpecialization();
    }

    @PostMapping("/add")
    public ResponseEntity<CommonApiResponse> addSpecialization(@RequestBody Specialization specialization) {
        return specializationService.addSpecialization(specialization);
    }

    @PutMapping("/update")
    public ResponseEntity<CommonApiResponse> updateSpecialization(@RequestBody Specialization specialization) {
        return specializationService.updateSpecialization(specialization);
    }

}
