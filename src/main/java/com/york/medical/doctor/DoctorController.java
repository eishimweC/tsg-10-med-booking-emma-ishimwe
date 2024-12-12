package com.york.medical.doctor;

import com.york.medical.appointment.AppointmentRepository;
import com.york.medical.specialization.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;


    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;

    }

    // Endpoint to fetch all the doctors
    @GetMapping
    public List<Doctor> findAllDoctors(){
        return doctorService.findAllDoctors();
    }

    @GetMapping("/specialization")
    public List<Doctor> findDoctorsBySpecializationId(@RequestParam Long id) {
        return this.doctorService.findDoctorsBySpecializationId(id);
    }

    @PostMapping("/create")
    public Doctor createNewDoctor(@RequestBody DoctorDTO doctorDTO) {
        return this.doctorService.createNewDoctor(doctorDTO);
    }

    @DeleteMapping("/del/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    // Endpoint to update a doctor
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
//        // Set the ID from the path variable to the doctor object
//        doctor.setId(id);
//        // Delegate the update logic to the service layer
//        return doctorService.updateDoctor(doctor);
//    }
}
