package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.UserPayrollDto;
import com.art.timelymanagementsystem.services.UserPayrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payroll")
public class PayrollController {

    private final UserPayrollService userPayrollService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPayrollDto> getAllUserPayroll(@PathVariable Long userId){

        return ResponseEntity.ok(userPayrollService.getAllUserPayrollService(userId));

    }

}
