package com.westminster.auth_service.dto;

import java.util.List;

public class LoginResponse {
    public String accessToken;
    public long expiresAt;
    private List<String> roles;
    public LoginResponse(String token, long exp,List<String> roles) { this.accessToken = token; this.expiresAt = exp; this.roles = roles;}
}
