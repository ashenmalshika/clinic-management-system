package com.westminster.auth_service.service;

import com.westminster.auth_service.client.DoctorClient;
import com.westminster.auth_service.client.PatientClient;
import com.westminster.auth_service.dto.DoctorProfileRequest;
import com.westminster.auth_service.dto.DoctorRequest;
import com.westminster.auth_service.dto.PatientRequest;
import com.westminster.auth_service.dto.RegisterRequest;
import com.westminster.auth_service.model.AppUser;
import com.westminster.auth_service.repository.UserRepository;
import com.westminster.auth_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PatientClient patientClient;
    @Autowired
    private DoctorClient doctorClient;


    public AppUser register(RegisterRequest req) {
        var u = new AppUser();
        u.setUsername(req.username);
        u.setPassword(passwordEncoder.encode(req.password));
        u.setRoles(req.roles == null ? "ROLE_RECEPTIONIST" : req.roles);
        return repo.save(u);
    }

    public String loginToken(String username) {
        var u = repo.findByUsername(username).orElseThrow();
        return jwtUtil.generateToken(u.getUsername(), u.getRoles(), u.getId());
    }

    public String registerPatient(PatientRequest request) {
        // 1. Save login credentials (user table)
        var user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles("PATIENT");
        repo.save(user);

        // 2️⃣ Send patient data to PatientService
        PatientRequest patientReq = new PatientRequest();
        patientReq.setName(request.getName());
        patientReq.setNic(request.getNic());
        patientReq.setPhone(request.getPhone());
        patientReq.setDob(request.getDob());
        patientReq.setAddress(request.getAddress());
        patientReq.setUsername(request.getUsername());

        patientClient.createPatient(patientReq);

        return "Patient registered successfully!";
    }
    public String registerDoctor(String authToken,DoctorRequest request) {
        var user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles("DOCTOR");
        AppUser savedUser = repo.save(user);

        //send doctor data to DoctorService
        DoctorProfileRequest docReq = new DoctorProfileRequest();
        docReq.setUserId(savedUser.getId());
        docReq.setName(request.getName());
        docReq.setEmail(request.getEmail());
        docReq.setSpecialization(request.getSpecialization());


        doctorClient.createDoctor(authToken,docReq);
        return "Doctor registered successfully!";
    }
}