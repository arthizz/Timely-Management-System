package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.dto.TimeLogPauseDto;
import com.art.timelymanagementsystem.dto.TotalWorkHoursDto;
import com.art.timelymanagementsystem.request.TimeLogRequest;
import com.art.timelymanagementsystem.services.TimeLogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/timelog")
public class TimeLogController {

    private final TimeLogService timeLogService;

    @GetMapping
    public ResponseEntity<List<TimeLogDto>> getAllTimeLog(){

        return ResponseEntity.ok(timeLogService.getAllTimeLogService());

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeLogDto> getSingleTimeLog(@PathVariable Long id){

        return ResponseEntity.ok(timeLogService.getSingleTimeLogService(id));

    }

    @PostMapping
    public ResponseEntity<TimeLogDto> clockInTimeLog(@Valid @RequestBody TimeLogRequest timeLogRequest){

        return timeLogService.clockInTimeLogService(timeLogRequest);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDto> clockOutTimeLog(@PathVariable Long id){

        return timeLogService.clockOutTimeLogService(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteTimeLog(@PathVariable Long id){

        return ResponseEntity.ok(timeLogService.deleteTimeLogService(id));

    }

    @GetMapping("/duration/{userId}")
    public ResponseEntity<TotalWorkHoursDto> getHoursDifference(@PathVariable Long userId){

        return ResponseEntity.ok(timeLogService.getTotalHoursService(userId));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TimeLogDto>> getUserTimeLogs(@PathVariable Long userId){

        return ResponseEntity.ok(timeLogService.getUserTimeLogsService(userId));

    }

    @PostMapping("/pause/{timeLogId}")
    public ResponseEntity<TimeLogPauseDto> pauseCurrentTimeLog(@PathVariable Long timeLogId){

        return ResponseEntity.ok(timeLogService.pauseCurrentTimeLogService(timeLogId));

    }

    @PostMapping("/resume/{timeLogPauseId}")
    public ResponseEntity<TimeLogPauseDto> resumeCurrentTimeLog(@PathVariable Long timeLogPauseId){

        return ResponseEntity.ok(timeLogService.resumeCurrentTimeLogService(timeLogPauseId));

    }

    @GetMapping("/duration/{userId}")
    public ResponseEntity<TimeLogPauseDto> calculateUserWorkDuration(@PathVariable Long userId){

        return ResponseEntity.ok(timeLogService.calculateUserWorkDuration(userId));

    }

}
