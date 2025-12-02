package com.westminster.auth_service.client;

import com.westminster.auth_service.dto.PatientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// URL can be changed if you use service discovery like Eureka
@FeignClient(name = "patient-service", url = "http://localhost:8080")
public interface PatientClient {

    @PostMapping("/patient/patient/register")
    void createPatient(@RequestBody PatientRequest request);
}