package com.westminster.billing_service.controller;

import com.westminster.billing_service.model.Bill;
import com.westminster.billing_service.service.BillingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bills")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public List<Bill> getAll() {
        return service.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public Bill create(@RequestBody Bill bill) {
        return service.create(bill);
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','BILLING')")
    public List<Bill> getByPatient(@PathVariable Long id) {
        return service.getByPatient(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
