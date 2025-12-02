package com.westminster.appointment_service.service;

import com.westminster.appointment_service.model.Appointment;
import com.westminster.appointment_service.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public Appointment save(Appointment a) {
        return repo.save(a);
    }

    public List<Appointment> getAll() {
        return repo.findAll();
    }

    public List<Appointment> getByDoctor(Long id) {
        return repo.findByDoctorId(id);
    }

    public List<Appointment> getByPatient(Long id) {
        return repo.findByPatientId(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}