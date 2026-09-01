package com.art.timelymanagementsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserPayrollDto {

    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalHours;
    private BigDecimal totalPay;
    private BigDecimal hourlyRate;

}
