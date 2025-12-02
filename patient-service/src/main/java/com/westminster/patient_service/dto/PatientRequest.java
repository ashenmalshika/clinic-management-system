package com.westminster.patient_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String name;
    private String nic;
    private String phone;
    private LocalDate dob;
    private String address;
    private String username;
}
