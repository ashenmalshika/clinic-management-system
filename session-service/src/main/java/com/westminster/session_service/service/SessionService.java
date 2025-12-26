package com.westminster.session_service.service;
import com.westminster.session_service.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SessionService {
    boolean reserveSeat(Long sessionId);

    Session createSession(Session session);

    Page<Session> getSessionsByDoctor(Long doctorId, Pageable pageable);

    Session getSession(Long sessionId);

    boolean deleteSession(Long sessionId);
}
