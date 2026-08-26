package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

    List<TimeLog> findByUserId(Long userId);

    Optional<TimeLog> findTopByUserIdOrderByCreatedAtDesc(Long userId);
}
