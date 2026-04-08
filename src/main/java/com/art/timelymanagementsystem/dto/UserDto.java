package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.UserProfile;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Short userLevelId;
    private LocalDateTime createdAt;
    private UserProfile userProfile;
    private TimeLog timeLog;
}
