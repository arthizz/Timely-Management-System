package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TimeLogPauseDto {
    private Long id;
    private LocalDateTime timeResume;
    private LocalDateTime createdAt;
}
