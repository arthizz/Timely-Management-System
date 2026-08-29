package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.dto.UserPayrollDto;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.TimeLogRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserPayrollService {

    private final UserRepository userRepository;
    private final TimeLogRepository timeLogRepository;
    private final UserMapper userMapper;
    private final TimeLogMapper timeLogMapper;


    public UserPayrollDto getAllUserPayrollService(Long userId){

        UserDto userDto = userRepository.findById(userId).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        List<TimeLogDto> timeLogDto = timeLogRepository.findByUserId(userId).stream().map(timeLogMapper::toDto).toList();



    }

    public UserPayrollDto setUserPayRollData(TimeLogDto timeLogDto, UserDto userDto, LocalDate startDate, LocalDate endDate){

        UserPayrollDto userPayrollDto = new UserPayrollDto();

        userPayrollDto.setUserId(userDto.getId());
        userPayrollDto.setStartDate(startDate);
        userPayrollDto.setEndDate(endDate);
        userPayrollDto.setHourlyRate(userDto.getHourlyRate());

        return userPayrollDto;

    }

}
