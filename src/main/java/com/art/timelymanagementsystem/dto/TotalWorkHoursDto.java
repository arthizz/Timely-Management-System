package com.art.timelymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalWorkHoursDto {

    private Long totalHours;
    private Integer totalMinutes;
    private Integer totalSeconds;

}
