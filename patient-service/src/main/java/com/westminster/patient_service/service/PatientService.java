package com.westminster.patient_service.service;

import com.westminster.patient_service.dto.PatientDTO;
import com.westminster.patient_service.dto.PatientRequest;
import com.westminster.patient_service.model.Patient;
import com.westminster.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PatientService {


    @Autowired
    private PatientRepository repository;


    public List<PatientDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }


    public Optional<PatientDTO> getById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }


    public PatientDTO create(PatientDTO dto) {
        Patient p = new Patient(dto.getName(), dto.getNic(), dto.getPhone(), dto.getDob(), dto.getAddress(), dto.getUsername());
        Patient saved = repository.save(p);
        return toDTO(saved);
    }


    public Optional<PatientDTO> update(Long id, PatientDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setNic(dto.getNic());
            existing.setPhone(dto.getPhone());
            existing.setDob(dto.getDob());
            existing.setAddress(dto.getAddress());
            return toDTO(repository.save(existing));
        });
    }


    public void delete(Long id) { repository.deleteById(id); }


    private PatientDTO toDTO(Patient p) {
        PatientDTO dto = new PatientDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setNic(p.getNic());
        dto.setPhone(p.getPhone());
        dto.setDob(p.getDob());
        dto.setAddress(p.getAddress());
        return dto;
    }
    public void registerPatient(PatientRequest request) {
        Patient p = new Patient();
        p.setName(request.getName());
        p.setNic(request.getNic());
        p.setPhone(request.getPhone());
        p.setDob(request.getDob());
        p.setAddress(request.getAddress());
        p.setUsername(request.getUsername());
        repository.save(p);
    }
}