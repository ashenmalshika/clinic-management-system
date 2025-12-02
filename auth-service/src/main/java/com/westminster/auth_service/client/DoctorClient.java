package com.westminster.auth_service.client;

import com.westminster.auth_service.dto.DoctorProfileRequest;
import com.westminster.auth_service.dto.DoctorRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "doctor-service", url = "http://localhost:8080")
public interface DoctorClient {

    @PostMapping("/doctor/create")
    void createDoctor(@RequestHeader("Authorization") String authorizationHeader, @RequestBody DoctorProfileRequest request);
}
