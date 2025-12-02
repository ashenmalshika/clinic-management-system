package com.westminster.patient_service.controller;

import com.westminster.patient_service.dto.PatientDTO;
import com.westminster.patient_service.dto.PatientRequest;
import com.westminster.patient_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/v1/patients")
public class PatientController {


    @Autowired
    private PatientService service;


    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<PatientDTO> create(@RequestBody PatientDTO dto) {
        System.out.println("123");
        PatientDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/patients/" + created.getId())).body(created);
    }


    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    public ResponseEntity<PatientDTO> update(@PathVariable Long id, @RequestBody PatientDTO dto) {
        return service.update(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/patient/register")
    public ResponseEntity<String> registerPatient(@RequestBody PatientRequest request) {
        service.registerPatient(request);
        return ResponseEntity.ok("Patient registered successfully");
    }
}