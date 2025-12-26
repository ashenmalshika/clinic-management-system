package com.westminster.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "session-service", url = "http://localhost:9400")
public interface SessionClient {

    @PostMapping("/api/v1/sessions/{id}/reserve")
    Map<String, Boolean> reserve(@PathVariable Long id);
}
