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
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time_log_id")
    private Long timeLogId;

    @Column(name = "time_resume")
    private LocalDateTime timeResume;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
