package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeLogRequest {

    @NotNull(message = "User should login first to use time log")
    private Long userId;

    private Boolean inNightShift;

}
