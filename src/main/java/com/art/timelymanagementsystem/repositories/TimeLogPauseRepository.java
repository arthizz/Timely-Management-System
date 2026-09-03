package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.TimeLogPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TimeLogPauseRepository extends JpaRepository<TimeLogPause, Long> {

    @Query("""
    SELECT t
    FROM TimeLogPause t
    WHERE t.timeLog.id = :timeLogId
        AND t.timeResume IS NULL
    """)
    Optional<TimeLogPause> findCurrentActivePause(@Param("timeLogId") Long timeLogId);

    List<TimeLogPause> findByTimeLogId(Long timeLogId);

}
