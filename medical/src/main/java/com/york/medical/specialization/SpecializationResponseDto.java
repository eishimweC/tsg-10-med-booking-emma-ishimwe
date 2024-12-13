package com.york.medical.specialization;

import com.york.medical.dto.CommonApiResponse;

import java.util.ArrayList;
import java.util.List;

public class SpecializationResponseDto extends CommonApiResponse {
    private List<Specialization> specializations = new ArrayList<>();

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }
}