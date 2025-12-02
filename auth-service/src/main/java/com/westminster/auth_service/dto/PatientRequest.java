package com.westminster.auth_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String username;
    private String password;
    private String name;
    private String nic;
    private String phone;
    private LocalDate dob;
    private String address;
}
