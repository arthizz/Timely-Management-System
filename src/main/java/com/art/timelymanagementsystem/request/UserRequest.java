package com.art.timelymanagementsystem.request;

import com.art.timelymanagementsystem.entities.User;
import lombok.*;

import java.time.LocalDateTime;

@Data

public class UserRequest {

    private String userName;
    private String email;
    private String password;
    private Short userLevelId;

    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String profilePicture;

}
