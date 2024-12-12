package com.york.medical.appointment;

import com.york.medical.doctor.Doctor;
import com.york.medical.doctor.DoctorRepository;
import com.york.medical.patient.Patient;
import com.york.medical.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    // fetch a list of appointments by Patient id
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Get a List of all appointments
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    // Create a New Appointment
    public Appointment createNewAppointment(AppointmentDTO appointmentDTO) {
        // Validate required fields
        if (appointmentDTO.getAppointmentDateTime() == null) {
            System.out.println("Bad DateTime format");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment date and time is required.");
        }
        if (appointmentDTO.getVisitType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Visit type is required.");
        }

        // find doctor
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointmentDTO.getDoctorId());
        if (doctorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with ID: " + appointmentDTO.getDoctorId() + " not found");
        }

        // find patient
        Optional<Patient> patientOptional = patientRepository.findById(appointmentDTO.getPatientId());
        if (patientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with ID: " + appointmentDTO.getPatientId());
        }

        // Check for existing appointments
        LocalDate appointmentDate = appointmentDTO.getAppointmentDateTime().toLocalDate();

        List<Appointment> existingAppointments = appointmentRepository.findByDoctorIdAndPatientIdOnDate(
                appointmentDTO.getPatientId(),
                appointmentDTO.getDoctorId(),
                appointmentDate);

        if (!existingAppointments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An appointment with this doctor already exists for the given patient on the same day.");
        }

        // Create appointment object
        Appointment appointment = new Appointment(
                doctorOptional.get(),
                patientOptional.get(),
                appointmentDTO.getAppointmentDateTime(),
                appointmentDTO.getVisitType()
        );
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        // Save and return appointment
        return appointmentRepository.save(appointment);
    }

    // Cancel an Appointment
    public Appointment cancelAppointment(Long id) {
        Optional<Appointment> appointmentOptional = this.appointmentRepository.findById(id);
        if (appointmentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with ID " + id + " not found.");
        }

        // Retrieve the existing appointment
        Appointment existingAppointment = appointmentOptional.get();

        // Update the status to CANCELLED
        existingAppointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

        // Save and return the updated appointment
        return appointmentRepository.save(existingAppointment);
    }

    public Appointment updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

        if (optionalAppointment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment with ID " + id + " not found.");
        }

        Appointment existingAppointment = optionalAppointment.get();

        // Update appointment date and time
        if (appointmentDTO.getAppointmentDateTime() != null) {
            existingAppointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        }

        // Update visit type
        if (appointmentDTO.getVisitType() != null) {
            existingAppointment.setVisitType(appointmentDTO.getVisitType());
        }

        // Update appointment status
        if (appointmentDTO.getAppointmentStatus() != null) {
            existingAppointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
        }

        // Save updated appointment
        return appointmentRepository.save(existingAppointment);
    }



}
