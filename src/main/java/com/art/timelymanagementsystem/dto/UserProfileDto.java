package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String profilePicture;
    private LocalDateTime createdAt;

}
