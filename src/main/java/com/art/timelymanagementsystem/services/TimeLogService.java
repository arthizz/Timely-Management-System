package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.dto.TotalWorkHoursDto;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeLogService {

    private final TimeLogRepository timeLogRepository;
    private final TimeLogMapper timeLogMapper;
    private final UserRepository userRepository;

    public List<TimeLogDto> getAllTimeLogService(){

        return timeLogRepository.findAll().stream().map(timeLogMapper::toDto).toList();

    }

    public TimeLogDto getSingleTimeLogService(Long id){

        return timeLogRepository.findById(id).map(timeLogMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("TimeLog Not Found"));

    }

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
        timeLog.setIsNightShift(Boolean.TRUE.equals(timeLogRequest.getInNightShift()));

        timeLog.setCreatedAt(LocalDateTime.now());

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

    public ResponseEntity<TimeLogDto> updateTimeLogService(Long timeLogId){

        TimeLog timeLog = timeLogRepository.findById(timeLogId).orElseThrow(() -> new ResourceNotFoundException("TimeLog Does not Exists"));

        if(timeLog.getTimeOut() != null){

            throw new BadRequestException("User already timedOut, if you need to adjust please request it to the manager");

        }

        timeLog.setTimeOut(LocalDateTime.now());

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

    public MessageResponseDto deleteTimeLogService(Long id){

        TimeLog timeLog = timeLogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TimeLog not Found"));

        timeLogRepository.delete(timeLog);

        return new MessageResponseDto("Delete Time Log Success");

    }

    public TotalWorkHoursDto getTotalHoursService(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        TimeLog timeLogToday = timeLogRepository.findTimeLogOfToday(
                user.getId(),
                LocalDate.now().atStartOfDay(),
                LocalDate.now().plusDays(1).atStartOfDay()
        ).orElseThrow(() -> new ResourceNotFoundException("User does not have a Log yet"));

        if(timeLogToday.getTimeOut() == null){

            throw new BadRequestException("User has not time out yet");

        }

        var timeLogIn = timeLogToday.getTimeIn();
        var timeLogOut = timeLogToday.getTimeOut();

        Duration duration = Duration.between(timeLogIn, timeLogOut);

        return new TotalWorkHoursDto(duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());

    }

    public List<TimeLogDto> getUserTimeLogsService(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        return timeLogRepository.findByUserId(id).stream().map(timeLogMapper::toDto).toList();

    }

}
