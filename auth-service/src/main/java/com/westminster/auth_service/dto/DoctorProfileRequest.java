package com.westminster.auth_service.dto;

import lombok.Data;

@Data
public class DoctorProfileRequest {
    private Long userId;
    private String name;
    private String email;
    private String specialization;
}
