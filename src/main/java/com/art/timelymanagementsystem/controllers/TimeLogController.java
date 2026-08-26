package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.repositories.TimeLogRepository;
import com.art.timelymanagementsystem.request.TimeLogRequest;
import com.art.timelymanagementsystem.services.TimeLogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/timelog")
public class TimeLogController {

    private final TimeLogRepository timeLogRepository;
    private final TimeLogMapper timeLogMapper;
    private final TimeLogService timeLogService;

    @GetMapping
    public ResponseEntity<List<TimeLogDto>> getAllTimeLog(){

        List<TimeLogDto> timeLogDto = timeLogRepository.findAll().stream().map(timeLogMapper::toDto).toList();

        return ResponseEntity.ok(timeLogDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeLogDto> getSingleTimeLog(@PathVariable Long id){

        TimeLogDto timeLogDto = timeLogRepository.findById(id).map(timeLogMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("TimeLog Not Found"));

        return ResponseEntity.ok(timeLogDto);

    }

    @PostMapping
    public ResponseEntity<TimeLogDto> createTimeLog(@Valid @RequestBody TimeLogRequest timeLogRequest){

        return timeLogService.createTimeLogService(timeLogRequest);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDto> updateTimeLog(@PathVariable Long id, @Valid @RequestBody TimeLogRequest timeLogRequest){

        return timeLogService.updateTimeLogService(timeLogRequest, id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeLog(@PathVariable Long id){

        TimeLog timeLog = timeLogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TimeLog not Found"));

        timeLogRepository.delete(timeLog);

        return ResponseEntity.ok("Delete TimeLog Success");

    }

}
