package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.CallLog;

import java.time.LocalDateTime;
import java.util.List;

public interface CallLogRepository extends JpaRepository<CallLog, String> {
    List<CallLog> findByStartedAtBetween(LocalDateTime from, LocalDateTime to);
}
