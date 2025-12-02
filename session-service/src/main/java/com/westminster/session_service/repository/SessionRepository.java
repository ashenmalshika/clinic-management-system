package com.westminster.session_service.repository;

import com.westminster.session_service.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Page<Session> findByDoctorId(Long doctorId, Pageable pageable);
}

