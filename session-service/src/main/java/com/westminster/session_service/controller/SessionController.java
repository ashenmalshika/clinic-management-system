package com.westminster.session_service.controller;

import com.westminster.session_service.model.Session;
import com.westminster.session_service.service.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @PostMapping("/{id}/reserve")
    public Map<String, Boolean> reserve(@PathVariable Long id) {
        System.out.println("***1");
        boolean allowed = sessionService.reserveSeat(id);

        return Map.of("allowed", allowed);
    }

    @PostMapping("/create")
    public Session createSession(@RequestBody Session session) {
        return sessionService.createSession(session);
    }


    @GetMapping("/doctor/{doctorId}")
    public Page<Session> getDoctorSessions(
            @PathVariable Long doctorId,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return sessionService.getSessionsByDoctor(doctorId, pageable);
    }
    // DELETE endpoint to remove a session by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        boolean deleted = sessionService.deleteSession(id);

        if (deleted) {
            return ResponseEntity.ok("Session deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session not found");
        }
    }
}

