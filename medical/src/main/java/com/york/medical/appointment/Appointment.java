package com.york.medical.appointment;

import com.york.medical.doctor.Doctor;
import com.york.medical.patient.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table (name = "appointments")
public class Appointment {
    // Links patients with doctors

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @NotNull
    private LocalDateTime appointmentDateTime;

    @NotNull
    @ManyToOne
    @JoinColumn( name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @NotNull
    @ManyToOne
    @JoinColumn( name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus; //PENDING CONFIRMED OR CANCELLED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitType visitType; // IN_PERSON or TELEHEALTH

    public Appointment() {}

    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime, VisitType visitType) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.visitType = visitType;
    }

    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }
}
