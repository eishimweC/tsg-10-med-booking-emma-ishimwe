package com.york.medical.doctor;

import com.york.medical.appointment.AppointmentRepository;
import com.york.medical.specialization.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;

    }
    // Endpoint to fetch all the doctors
    @GetMapping
    public List<Doctor> findAllDoctors(){
        return doctorService.findAllDoctors();
    }

    @GetMapping(path = "/{id}")
    public Doctor findADoctorById(@PathVariable Long id) {
        return doctorService.findDoctorById(id);
    }

    @GetMapping("/specialization")
    public List<Doctor> findDoctorsBySpecializationId(@RequestParam Long id) {
        return this.doctorService.findDoctorsBySpecializationId(id);
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('Admin')")
    public Doctor createNewDoctor(@RequestBody DoctorDTO doctorDTO) {
        return this.doctorService.createNewDoctor(doctorDTO);
    }

    @DeleteMapping("/del/{id}")
    //@PreAuthorize("hasRole('Admin')")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    // Endpoint to update a doctor
    @PutMapping(path = "/{id}")
    //@PreAuthorize("hasRole('Admin')")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        return doctorService.updateDoctor(id, doctorDTO);
    }
}
