package com.york.medical.specialization;

import com.york.medical.dto.CommonApiResponse;
import com.york.medical.exception.SpecializationSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Autowired
    SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public ResponseEntity<CommonApiResponse> addSpecialization(Specialization specialization) {

        CommonApiResponse response = new CommonApiResponse();

        if (specialization == null) {
            response.setResponseMessage("Missing input!");
            response.setSuccess(false);
            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        //specialization.setStatus(Constants.ActiveStatus.ACTIVE.value());
        Specialization savedSpecialization = specializationRepository.save(specialization);

        if (savedSpecialization == null) {
            throw new SpecializationSaveFailedException("Failed to add specialization!");
        }
        response.setResponseMessage("Specialization Added Successful!");
        response.setSuccess(true);
        return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

    }

    public ResponseEntity<SpecializationResponseDto> fetchAllSpecialization() {
        SpecializationResponseDto response = new SpecializationResponseDto();

        // Fetch all specializations
        List<Specialization> specializations = specializationRepository.findAll();

        if (CollectionUtils.isEmpty(specializations)) {
            response.setResponseMessage("No Specializations found");
            response.setSuccess(false);
            response.setSpecializations(Collections.emptyList()); // Avoid returning null for lists
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        response.setSpecializations(specializations);
        response.setResponseMessage("Specializations fetched successfully!");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<CommonApiResponse> updateSpecialization(Specialization specialization) {

        CommonApiResponse response = new CommonApiResponse();

        if (specialization == null) {
            response.setResponseMessage("Missing input");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        if (specialization.getId() == 0) {
            response.setResponseMessage("missing specialization Id");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        //specialization.setStatus(Constants.ActiveStatus.ACTIVE.value());
        Specialization savedSpecialization = specializationRepository.save(specialization);

        if (savedSpecialization == null) {
            throw new SpecializationSaveFailedException("Failed to update specialization");
        }

        response.setResponseMessage("Specialization Updated Successful");
        response.setSuccess(true);

        return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

    }
}
