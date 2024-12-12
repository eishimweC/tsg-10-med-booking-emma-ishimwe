package com.york.medical.specialization;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "specializations")
public class Specialization {
    // Defines fields like Cardiology, Neurology, etc.
    // Specializations is a separate table linked to Doctor via a foreign key.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialization_id")
    private Long id;

    @NotBlank
    private String name; // e.g Cardiology, Neurology,

    public Specialization() {
    }

    public Specialization(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
