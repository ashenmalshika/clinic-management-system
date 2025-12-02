package com.westminster.billing_service.service;

import com.westminster.billing_service.model.Bill;
import com.westminster.billing_service.repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillingService {

    private final BillingRepository repo;

    public BillingService(BillingRepository repo) {
        this.repo = repo;
    }

    public Bill create(Bill bill) {
        bill.setCreatedAt(LocalDateTime.now());
        return repo.save(bill);
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    public List<Bill> getByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
