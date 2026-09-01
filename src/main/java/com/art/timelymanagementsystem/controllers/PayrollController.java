package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.UserPayrollDto;
import com.art.timelymanagementsystem.request.PayrollRequest;
import com.art.timelymanagementsystem.services.UserPayrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payroll")
public class PayrollController {

    private final UserPayrollService userPayrollService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPayrollDto> getUserPayroll(@PathVariable Long userId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate){

        return ResponseEntity.ok(userPayrollService.getUserPayrollService(userId, startDate, endDate));

    }

}
