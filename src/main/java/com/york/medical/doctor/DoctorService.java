package com.york.medical.doctor;

import com.york.medical.appointment.Appointment;
import com.york.medical.appointment.AppointmentRepository;
import com.york.medical.specialization.Specialization;
import com.york.medical.specialization.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, SpecializationRepository specializationRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Method to fetch all the doctors
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
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
//    public ResponseEntity<?> updateDoctor(Doctor doctor) {
//        if (doctor.getId() == null) {
//            return new ResponseEntity<>("Doctor ID cannot be null !", HttpStatus.BAD_REQUEST);
//        }
//
//        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctor.getId());
//
//        // Get the existing doctor entity
//        Doctor existingDoctor = optionalDoctor.get();
//
//        // Update the first name if provided
//        if (doctor.getFirstName() != null) {
//            existingDoctor.setFirstName(doctor.getFirstName());
//        }
//
//        // Update the last name if provided
//        if (doctor.getLastName() != null) {
//            existingDoctor.setLastName(doctor.getLastName());
//        }
//        // update specialization if provided
//        if (doctor.getSpecialization() != null && doctor.getSpecialization().getName() != null) {
//            Optional <Specialization> optionalSpecialization = specializationRepository.findByName(doctor.getSpecialization().getName());
//            if (optionalSpecialization.isEmpty()) {
//                return new ResponseEntity<>("Specialization with name '" + doctor.getSpecialization().getName() + "' not found!", HttpStatus.NOT_FOUND);
//            }
//            existingDoctor.setSpecialization(optionalSpecialization.get());
//        }
//        // Save the updated doctor
//        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
//
//        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
//    }


}
