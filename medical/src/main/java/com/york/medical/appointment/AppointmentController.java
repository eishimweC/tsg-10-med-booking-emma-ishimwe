package com.york.medical.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path= "/api/appointments")
//@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    // Endpoint to get a List of all the appointments associated to a Patient
    @GetMapping("/patient")
    public List<Appointment> getAppointmentPatientById (@RequestParam Long id) {
        // http://localhost:8080/api/appointments/patient?id=2
        return appointmentService.getAppointmentsByPatientId(id);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Endpoint to create a new appointment
    @PostMapping(path="/create")
    public Appointment createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return this.appointmentService.createNewAppointment(appointmentDTO);
    }

    // Endpoint to cancel an appointment
    @PutMapping("/cancel/{id}")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return this.appointmentService.cancelAppointment(id);
    }
    // Endpoint to update an existing appointment
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return this.appointmentService.updateAppointment(id, appointmentDTO);
    }


}
