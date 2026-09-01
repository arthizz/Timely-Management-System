package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PayrollRequest {

    @NotNull(message = "Start date must be provided")
    private LocalDateTime startDate;

    @NotNull(message = "End date must be provided")
    private LocalDateTime endDate;

}
