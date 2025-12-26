package com.westminster.appointment_service.controller;

import com.westminster.appointment_service.model.Appointment;
import com.westminster.appointment_service.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public List<Appointment> getAll() {
        return service.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Appointment dto) {
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/doctor/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<Appointment> byDoctor(@PathVariable Long id) {
        return service.getByDoctor(id);
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public List<Appointment> byPatient(@PathVariable Long id) {
        return service.getByPatient(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}