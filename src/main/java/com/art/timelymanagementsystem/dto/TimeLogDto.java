package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLogPause;
import com.art.timelymanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TimeLogDto {

    private Long id;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private Boolean inNightShift;
    private LocalDateTime CreatedAt;
//    private List<TimeLogPauseDto> timeLogPause;


}
