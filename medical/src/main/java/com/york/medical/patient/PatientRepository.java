package com.york.medical.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    // Derived query to find a patient by firstName, lastName, and dateOfBirth
    Optional<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

    List<Patient> findPatientsByFirstNameAndLastNameAndDateOfBirth(String firstName,
                                                                   String lastName,
                                                                   LocalDate dateOfBirth);
    Optional <Patient> findPatientByEmail(String email);
}
