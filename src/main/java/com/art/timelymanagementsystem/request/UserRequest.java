package com.art.timelymanagementsystem.request;

import com.art.timelymanagementsystem.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data

public class UserRequest {

    @NotBlank(message = "Username Required")
    private String userName;

    @Email(message = "Please input a proper email format")
    @NotBlank(message = "Email Required")
    private String email;

    @NotBlank(message = "Password Required")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String password;

    @NotNull(message = "User Level Required")
    private Short userLevelId;

    @NotBlank(message = "First name Required")
    private String firstName;

    @NotBlank(message = "Last name Required")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Address Required")
    private String address;

    @NotBlank(message = "Company Role Required")
    private Long roleId;

    private String profilePicture;

}
