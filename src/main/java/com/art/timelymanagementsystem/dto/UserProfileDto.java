package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserProfileDto {

    private Long id;
    private User user;
    private String firstName;
    private String LastName;
    private String middleName;
    private String address;
    private String profilePicture;
    private LocalDateTime createdAt;

}
