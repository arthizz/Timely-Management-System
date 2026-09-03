package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.dto.TimeLogPauseDto;
import com.art.timelymanagementsystem.dto.TotalWorkHoursDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.TimeLogPause;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.mappers.TimeLogPauseMapper;
import com.art.timelymanagementsystem.repositories.TimeLogPauseRepository;
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
    private final TimeLogPauseRepository timeLogPauseRepository;
    private final TimeLogPauseMapper timeLogPauseMapper;

    public List<TimeLogDto> getAllTimeLogService(){

        return timeLogRepository.findAll().stream().map(timeLogMapper::toDto).toList();

    }

    public TimeLogDto getSingleTimeLogService(Long id){

        return timeLogRepository.findById(id).map(timeLogMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("TimeLog Not Found"));

    }

    public ResponseEntity<TimeLogDto> clockInTimeLogService(TimeLogRequest timeLogRequest){

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

        timeLogRepository.save(timeLog);

        return ResponseEntity.ok(timeLogMapper.toDto(timeLog));

    }

    public ResponseEntity<TimeLogDto> clockOutTimeLogService(Long timeLogId){

        TimeLog timeLog = timeLogRepository.findById(timeLogId).orElseThrow(() -> new ResourceNotFoundException("TimeLog Does not Exists"));

        if(timeLog.getTimeOut() != null){

            throw new BadRequestException("User already timedOut, if you need to adjust please request it to the manager");

        }

        TimeLogPause activePause = timeLogPauseRepository.findCurrentActivePause(timeLogId).orElse(null);

        if(activePause != null){

            throw new BadRequestException("Please resume your break, before clocking out");

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

        List<TimeLogPauseDto> timeLogPauseDtos = timeLogPauseRepository.findByTimeLogId(timeLogToday.getId()).stream().map(timeLogPauseMapper::toDto).toList();

        Duration breakDuration = Duration.ZERO;

        for (TimeLogPauseDto timeLogPauseDto : timeLogPauseDtos){

            if(timeLogPauseDto.getTimeResume() == null){

                throw new BadRequestException("There is an error on your time log pause record");

            }

            if(timeLogPauseDto.getTimePause() == null){

                throw new BadRequestException("There is an error on your time log pause record");

            }

            Duration duration = Duration.between(timeLogPauseDto.getTimePause(), timeLogPauseDto.getTimeResume());

            breakDuration = breakDuration.plus(duration);

        }

        if(timeLogToday.getTimeOut() == null){

            throw new BadRequestException("User has not time out yet");

        }

        var timeLogIn = timeLogToday.getTimeIn();
        var timeLogOut = timeLogToday.getTimeOut();

        Duration duration = Duration.between(timeLogIn, timeLogOut);

        return new TotalWorkHoursDto(duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart(), breakDuration.toHours(), breakDuration.toMinutesPart(), breakDuration.toSecondsPart());

    }

    public List<TimeLogDto> getUserTimeLogsService(Long userId){

        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        return timeLogRepository.findByUserId(userId).stream().map(timeLogMapper::toDto).toList();

    }

    public TimeLogPauseDto pauseCurrentTimeLogService(Long timeLogId){

        TimeLog timeLog = timeLogRepository.findById(timeLogId).orElseThrow(() -> new ResourceNotFoundException("Time Log Not Exists"));

        TimeLogPause activePause = timeLogPauseRepository.findCurrentActivePause(timeLogId).orElse(null);

        if(timeLog.getTimeOut() != null){

            throw new BadRequestException("Cannot pause a completed timelog");

        }

        if(activePause != null){

            throw new BadRequestException("You are currently on pause break, please resume your time log");

        }

        TimeLogPause timeLogPause = new TimeLogPause();

        timeLogPause.setTimeLog(timeLog);
        timeLogPause.setTimePause(LocalDateTime.now());

        TimeLogPause newPause = timeLogPauseRepository.save(timeLogPause);

        return timeLogPauseMapper.toDto(newPause);

    }

    public TimeLogPauseDto resumeCurrentTimeLogService(Long timeLogPauseId){

        TimeLogPause timeLogPause = timeLogPauseRepository.findById(timeLogPauseId).orElseThrow(() -> new ResourceNotFoundException("Please pause your current timelog to resume"));

        TimeLog timeLog = timeLogPause.getTimeLog();

        if(timeLog.getTimeOut() != null){

            throw new BadRequestException("Cannot Resume a completed time log");

        }

        if(timeLogPause.getTimePause() == null){

            throw new BadRequestException("Pause record is invalid");

        }

        if(timeLogPause.getTimeResume() != null){

            throw new BadRequestException("Break has already been resumed");

        }

        timeLogPause.setTimeResume(LocalDateTime.now());

        TimeLogPause updateTimeLogPause = timeLogPauseRepository.save(timeLogPause);

        return timeLogPauseMapper.toDto(updateTimeLogPause);

    }

    public TimeLogPauseDto calculateUserWorkDuration(Long userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));



    }

}
