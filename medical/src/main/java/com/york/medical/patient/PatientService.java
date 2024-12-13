package com.york.medical.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Get a List of all Patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findPatientByEmail(String email) {
        return patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                "No Patient associated with this email: " + email));
    }

    public List<Patient> lookUpPatients(String firstName, String lastName, LocalDate dateOfBirth) {
        return patientRepository.findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
    }

    public Patient createNewPatient(Patient patient){
        return patientRepository.save(patient);
    }

    public Patient findPatientById(Long id){
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if(optionalPatient.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Patient associated with this id");
        }
        return optionalPatient.get();
    }
}
