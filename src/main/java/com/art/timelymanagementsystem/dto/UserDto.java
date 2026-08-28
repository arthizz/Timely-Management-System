package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.UserProfile;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String email;
    private Short userLevelId;
    private BigDecimal hourlyRate;
    private LocalDateTime createdAt;
    private UserProfileDto userProfile;
}
