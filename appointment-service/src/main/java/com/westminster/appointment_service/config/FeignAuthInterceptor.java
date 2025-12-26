package com.westminster.appointment_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            var attributes = RequestContextHolder.getRequestAttributes();

            if (attributes == null) return;

            var request = ((ServletRequestAttributes) attributes).getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && !authHeader.isBlank()) {
                template.header("Authorization", authHeader);
            }
        };
    }
}

