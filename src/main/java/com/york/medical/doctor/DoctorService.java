package com.york.medical.doctor;

import com.york.medical.appointment.Appointment;
import com.york.medical.appointment.AppointmentRepository;
import com.york.medical.specialization.Specialization;
import com.york.medical.specialization.SpecializationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    // Dependencies injected via constructor
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository, SpecializationRepository specializationRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Method to fetch all the doctors
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
    }

    public List<Doctor> findDoctorsBySpecializationId(Long specializationId) {
        return this.doctorRepository.findBySpecializationId(specializationId);
    }

    public Doctor createNewDoctor(DoctorDTO doctorDTO) {

        Optional<Specialization> specializationOptional = specializationRepository.findById(doctorDTO.getSpecializationId());
        if (specializationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization Not Found");
        }
        Doctor doctor = new Doctor(
                doctorDTO.getFirstName(),
                doctorDTO.getLastName(),
                specializationOptional.get()
        );
        return this.doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        this.appointmentRepository.deleteAll(appointments);
        this.doctorRepository.deleteById(id);
    }

    // Update Doctor
    @Transactional
    public Doctor updateDoctor(Long id, DoctorDTO doctorDTO) {
        // Find the existing doctor by ID
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        // Update fields if they are provided in the DTO
        if (doctorDTO.getFirstName() != null && !doctorDTO.getFirstName().isBlank()) {
            existingDoctor.setFirstName(doctorDTO.getFirstName());
        }

        if (doctorDTO.getLastName() != null && !doctorDTO.getLastName().isBlank()) {
            existingDoctor.setLastName(doctorDTO.getLastName());
        }

        if (doctorDTO.getSpecializationId() != null) {
            // Find the specialization by ID
            Specialization specialization = specializationRepository.findById(doctorDTO.getSpecializationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization not found"));
            existingDoctor.setSpecialization(specialization);
        }

        // Save the updated doctor
         doctorRepository.save(existingDoctor);
        return existingDoctor;
    }



}
