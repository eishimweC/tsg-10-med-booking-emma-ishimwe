package com.york.medical.doctor;

import com.york.medical.specialization.Specialization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "specialization_id")
    private Specialization specialization;

    //private String status; // Active or Inactive

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, Specialization specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
