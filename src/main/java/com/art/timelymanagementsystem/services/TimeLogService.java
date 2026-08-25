package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.repositories.TimeLogRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.TimeLogRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final TimeLogMapper timeLogMapper;
    private final UserRepository userRepository;

    public ResponseEntity<TimeLogDto> createTimeLogService(TimeLogRequest timeLogRequest){

        User user = userRepository.findById(timeLogRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TimeLog timeLog = new TimeLog();
        timeLog.setUser(user);
        timeLog.setTimeIn(LocalDateTime.now());

        if(timeLogRequest.getInNightShift() == null){

            timeLog.setIsNightShift(false);

        }

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

}
