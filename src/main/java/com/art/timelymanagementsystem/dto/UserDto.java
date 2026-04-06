package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.UserProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {
    private int id;
    private String name;
    private String email;
    private Short userLevelId;
    private LocalDateTime createdAt;
    private UserProfile userProfile;
    private TimeLog timeLog;
}
