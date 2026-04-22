package com.art.timelymanagementsystem.request;

import com.art.timelymanagementsystem.entities.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter

public class UserRequest {

    private String username;
    private String email;
    private String password;
    private Short userLevelId;
    private LocalDateTime createdAt;

    private String firstName;
    private String lastName;
    private Short userId;
    private String middleName;
    private String address;
    private String profilePicture;

}
