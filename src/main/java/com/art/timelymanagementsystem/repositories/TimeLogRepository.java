package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

    List<TimeLog> findByUserId(Long userId);

    Optional<TimeLog> findTopByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
        SELECT t
        FROM TimeLog t
        WHERE t.user.id = :userId
            AND t.timeIn >= :startOfDay
            AND t.timeIn < :startOfNextDay
    """)
    Optional<TimeLog> findTimeLogOfToday(
            @Param("userId") Long userId,
            @Param("startOfDay")LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay);

    @Query("""
        SELECT t
        FROM TimeLog t
        WHERE t.user.id = :userId
            AND t.createdAt >= :startDate
            AND t.createdAt < :endDate
    """)
    List<TimeLog> findUserTimeLogByDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
            );
}
