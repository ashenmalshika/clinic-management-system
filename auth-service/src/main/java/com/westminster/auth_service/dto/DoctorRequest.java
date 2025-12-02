package com.westminster.auth_service.dto;

import lombok.Data;

@Data
public class DoctorRequest {
    private String name;
    private String specialization;
    private String email;
    private String username;
    private String password;
}
