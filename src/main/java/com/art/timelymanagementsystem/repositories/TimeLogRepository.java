package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
}
