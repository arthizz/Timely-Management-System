package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLog;

import java.time.LocalDateTime;

public class TimeLogPauseDto {
    private Long id;
    private Long timeLogId;
    private LocalDateTime timeResume;
    private LocalDateTime createdAt;
}
