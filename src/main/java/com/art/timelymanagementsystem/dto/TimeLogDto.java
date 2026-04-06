package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLogPause;
import com.art.timelymanagementsystem.entities.User;

import java.time.LocalDateTime;

public class TimeLogDto {

    private Long id;
    private User user;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Boolean inNightShift;
    private LocalDateTime CreatedAt;
    private TimeLogPause timeLogPause;


}
