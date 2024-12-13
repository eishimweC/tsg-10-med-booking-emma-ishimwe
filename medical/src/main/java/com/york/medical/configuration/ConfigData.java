package com.york.medical.configuration;


import com.york.medical.appointment.Appointment;
import com.york.medical.appointment.AppointmentRepository;
import com.york.medical.appointment.VisitType;
import com.york.medical.doctor.Doctor;
import com.york.medical.doctor.DoctorRepository;
import com.york.medical.patient.Patient;
import com.york.medical.patient.PatientRepository;
import com.york.medical.specialization.Specialization;
import com.york.medical.specialization.SpecializationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ConfigData {

    @Bean
    public CommandLineRunner commandLineRunner(
            SpecializationRepository specializationRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository) {
        return args -> {

            // Create Specializations
            List<Specialization> specializations = List.of(
                    new Specialization("Cardiology"),
                    new Specialization("Pediatrics"),
                    new Specialization("Dermatology"),
                    new Specialization("Orthopedics"),
                    new Specialization("Neurology")
            );
            specializationRepository.saveAll(specializations);

            // Create Doctors
            List<Doctor> doctors = List.of(
                    new Doctor("John", "Doe", specializations.get(0)),
                    new Doctor("Jane", "Smith", specializations.get(1)),
                    new Doctor("Michael", "Brown", specializations.get(2)),
                    new Doctor("Emily", "Davis", specializations.get(3)),
                    new Doctor("Robert", "Wilson", specializations.get(4)),
                    new Doctor("Laura", "Garcia", specializations.get(0)),
                    new Doctor("Daniel", "Martinez", specializations.get(1)),
                    new Doctor("Sophia", "Anderson", specializations.get(2)),
                    new Doctor("Matthew", "Thomas", specializations.get(3)),
                    new Doctor("Olivia", "Taylor", specializations.get(4))
            );
            doctorRepository.saveAll(doctors);

            // Create Patients
            List<Patient> patients = List.of(
                    new Patient("Alice", "Johnson", LocalDate.of(1990, 1, 10), "alice.johnson@example.com"),
                    new Patient("Bob", "Williams", LocalDate.of(1985, 5, 15), "bob.williams@example.com"),
                    new Patient("Charlie", "Jones", LocalDate.of(1992, 8, 20), "charlie.jones@example.com"),
                    new Patient("Diana", "Miller", LocalDate.of(1980, 12, 25), "diana.miller@example.com"),
                    new Patient("Ethan", "Taylor", LocalDate.of(1995, 3, 30), "ethan.taylor@example.com"),
                    new Patient("Fiona", "Clark", LocalDate.of(1988, 7, 5), "fiona.clark@example.com"),
                    new Patient("George", "Lopez", LocalDate.of(1991, 9, 12), "george.lopez@example.com"),
                    new Patient("Hannah", "Hill", LocalDate.of(1993, 11, 18), "hannah.hill@example.com"),
                    new Patient("Isaac", "Scott", LocalDate.of(1984, 6, 22), "isaac.scott@example.com"),
                    new Patient("Julia", "Adams", LocalDate.of(1997, 4, 8), "julia.adams@example.com")
            );
            patientRepository.saveAll(patients);

            // Create Appointments
            List<Appointment> appointments = List.of(
                    new Appointment(doctors.get(0), patients.get(0), LocalDateTime.of(2024, 12, 15, 10, 0), VisitType.IN_PERSON),
                    new Appointment(doctors.get(1), patients.get(1), LocalDateTime.of(2024, 12, 16, 14, 30), VisitType.TELEHEALTH),
                    new Appointment(doctors.get(2), patients.get(2), LocalDateTime.of(2024, 12, 17, 9, 0), VisitType.IN_PERSON)
            );
            appointmentRepository.saveAll(appointments);
        };
    }

}
