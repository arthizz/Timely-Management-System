package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.TimeLogDto;
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
    public ResponseEntity<TimeLogDto> createTimeLog(@Valid @RequestBody TimeLogRequest timeLogRequest){

        return timeLogService.createTimeLogService(timeLogRequest);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeLogDto> updateTimeLog(@PathVariable Long id){

        return timeLogService.updateTimeLogService(id);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteTimeLog(@PathVariable Long id){

        return ResponseEntity.ok(timeLogService.deleteTimeLogService(id));

    }

}
