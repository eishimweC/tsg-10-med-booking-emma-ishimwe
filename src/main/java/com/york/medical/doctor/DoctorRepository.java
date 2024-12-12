package com.york.medical.doctor;

import com.york.medical.specialization.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    //Optional<Doctor> findByFirstNameAndLastNameAndSpecialization(String firstName, String lastName, Specialization specialization);
    List<Doctor> findBySpecializationId(Long specializationId);



}
