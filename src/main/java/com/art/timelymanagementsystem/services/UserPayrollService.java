package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.TimeLogDto;
import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.dto.UserPayrollDto;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.TimeLogMapper;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.TimeLogRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserPayrollService {

    private final UserRepository userRepository;
    private final TimeLogRepository timeLogRepository;
    private final UserMapper userMapper;
    private final TimeLogMapper timeLogMapper;


    public UserPayrollDto getUserPayrollService(Long userId, LocalDate startDate, LocalDate endDate){

        UserDto userDto = userRepository.findById(userId).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        LocalDateTime payrollStartDate = startDate.atStartOfDay();
        LocalDateTime payrollEndDate = endDate.plusDays(1).atStartOfDay();

        List<TimeLogDto> timeLogDtos = timeLogRepository.findUserTimeLogByDateRange(userId, payrollStartDate, payrollEndDate).stream().map(timeLogMapper::toDto).toList();

        if(userDto.getHourlyRate().compareTo(BigDecimal.ZERO) <= 0){

            throw new BadRequestException("User currently does not have an hourly rate, please set it up first");

        }

        if(timeLogDtos.isEmpty()){

            throw new ResourceNotFoundException("User does not have any time logs for the selected date range");

        }

        Duration totalDuration = Duration.ZERO;

        for (TimeLogDto timeLogDto : timeLogDtos){

            if(timeLogDto.getTimeOut() == null){

                throw new BadRequestException("User have a incomplete time logs please fix it first to calculate the work hours");

            }

            Duration duration = Duration.between(timeLogDto.getTimeIn(), timeLogDto.getTimeOut());

            totalDuration = totalDuration.plus(duration);

        }

        return this.setUserPayRollData(userDto, startDate, endDate, totalDuration);

    }

    public UserPayrollDto setUserPayRollData(UserDto userDto, LocalDate startDate, LocalDate endDate, Duration totalDuration){

        BigDecimal totalHoursWork = BigDecimal.valueOf(totalDuration.toSeconds()).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);

        BigDecimal totalPay = userDto.getHourlyRate().multiply(totalHoursWork).setScale(2, RoundingMode.HALF_UP);

        UserPayrollDto userPayrollDto = new UserPayrollDto();

        userPayrollDto.setUserId(userDto.getId());
        userPayrollDto.setStartDate(startDate);
        userPayrollDto.setEndDate(endDate);
        userPayrollDto.setHourlyRate(userDto.getHourlyRate());
        userPayrollDto.setTotalPay(totalPay);
        userPayrollDto.setTotalHours(totalHoursWork);

        return userPayrollDto;

    }

}
