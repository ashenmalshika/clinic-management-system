package com.westminster.auth_service.controller;

import com.westminster.auth_service.dto.*;
import com.westminster.auth_service.model.AppUser;
import com.westminster.auth_service.repository.UserRepository;
import com.westminster.auth_service.service.AuthService;
import com.westminster.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private AuthService authService;
    @Autowired private UserRepository repo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (repo.findByUsername(req.username).isPresent()) return ResponseEntity.badRequest().body("User exists");
        AppUser saved = authService.register(req);
        return ResponseEntity.ok(saved.getUsername());
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(authService.registerPatient(request));
    }
    @PostMapping("/signup/doctor")
    public ResponseEntity<?> registerDoctor(@RequestHeader("Authorization") String authToken,@RequestBody DoctorRequest request) {
        return ResponseEntity.ok(authService.registerDoctor(authToken,request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        System.out.println("******");
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.username, req.password));
            String token = authService.loginToken(req.username);
            var exp = jwtUtil.validate(token).getBody().getExpiration().getTime();
            List<String> roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            System.out.println(roles);
            return ResponseEntity.ok(new LoginResponse(token, exp,roles));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/publicKey")
    public ResponseEntity<?> getPublicKey() {
        var pk = jwtUtil.getPublicKey();
        String pem = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getEncoder().encodeToString(pk.getEncoded()) +
                "\n-----END PUBLIC KEY-----";
        return ResponseEntity.ok(Map.of("publicKey", pem));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateHeader(@RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) return ResponseEntity.badRequest().build();
        try {
            var claims = jwtUtil.validate(auth.substring(7));
            return ResponseEntity.ok(claims.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
