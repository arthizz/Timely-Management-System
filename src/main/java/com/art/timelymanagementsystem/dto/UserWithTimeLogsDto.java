package com.art.timelymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithTimeLogsDto {

    private Long id;
    private String userName;
    private String email;
    private Short userLevelId;
    private LocalDateTime createdAt;
    private List<TimeLogDto> timeLogs;

}
