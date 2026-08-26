package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.repositories.TimeLogRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.TimeLogRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final TimeLogMapper timeLogMapper;
    private final UserRepository userRepository;

    public ResponseEntity<TimeLogDto> createTimeLogService(TimeLogRequest timeLogRequest){

        User user = userRepository.findById(timeLogRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<TimeLog> timeLogExist = timeLogRepository.findTimeLogOfToday(user.getId(), LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay());

        if(timeLogExist.isPresent()){

            throw new BadRequestException("User already has time log for today, please Update your timelog to time in / time out");

        }

        TimeLog timeLog = new TimeLog();
        timeLog.setUser(user);
        timeLog.setTimeIn(LocalDateTime.now());
        timeLog.setTimeOut(null);

        if(timeLogRequest.getInNightShift() == null){

            timeLog.setIsNightShift(false);

        }
        timeLog.setCreatedAt(LocalDateTime.now());

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

    public ResponseEntity<TimeLogDto> updateTimeLogService(TimeLogRequest timeLogRequest, Long timeLogId){

        TimeLog timeLog = timeLogRepository.findById(timeLogId).orElseThrow(() -> new ResourceNotFoundException("TimeLog Does not Exists"));

        if(timeLog.getTimeOut() == null && timeLogRequest.getIsTimingIn() == true){

            throw new BadRequestException("User is currently timed in, Request should be timeOut");

        }

        System.out.println(timeLog.getTimeOut());

        if(timeLog.getTimeOut() != null && timeLogRequest.getIsTimingOut() == true){

            throw new BadRequestException("User is currently Timed out, Request should be time in");

        }

        if(timeLogRequest.getIsTimingIn() || timeLogRequest.getIsTimingIn() == null){

            timeLog.setTimeOut(null);
            timeLogRepository.save(timeLog);
            return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

        }

        timeLog.setTimeOut(LocalDateTime.now());

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

}
