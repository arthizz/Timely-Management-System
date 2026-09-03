package com.art.timelymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class TimeLogPause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time_log_id", nullable = false)
    private TimeLog timeLog;

    @Column(name = "time_pause", nullable = true)
    private LocalDateTime timePause;

    @Column(name = "time_resume", nullable = false)
    private LocalDateTime timeResume;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate(){

        createdAt = LocalDateTime.now();

    }

}
