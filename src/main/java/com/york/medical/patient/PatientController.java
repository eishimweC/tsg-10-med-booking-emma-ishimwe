package com.york.medical.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path ="/api/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Endpoint to get all patients
    @GetMapping
    public List <Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    // Endpoint to get Patient by id
    @GetMapping(path = "/{id}")
    //@PreAuthorize("hasAnyAuthority('Patients', 'Admin')")
    public Patient findPatientById(@PathVariable Long id) {
       return patientService.findPatientById(id);
    }

    //End point to find a Patient by email
    @GetMapping("/e")
    public Patient findPatientByEmail(@RequestParam String email){
        return patientService.findPatientByEmail(email);
    }

    // Endpoint to create a Patient
    @PostMapping
    //@PreAuthorize("hasAnyAuthority('Patients', 'Admin')")
    public Patient createNewPatient(@RequestBody Patient patient) {
        System.out.println("Patient : " + patient + " created");
        return patientService.createNewPatient(patient);
    }

    @GetMapping("/lookup")
    public List<Patient> lookUpPatients(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam LocalDate dateOfBirth) {
        return this.patientService.lookUpPatients(firstName, lastName, dateOfBirth);
    }
}
