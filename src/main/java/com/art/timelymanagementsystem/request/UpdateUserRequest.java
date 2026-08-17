package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Username Required")
    private String userName;

    @Email(message = "Please input a proper email format")
    @NotBlank(message = "Email Required")
    private String email;

    @NotNull(message = "User Level Required")
    private Short userLevelId;

    @NotBlank(message = "First name Required")
    private String firstName;

    @NotBlank(message = "Last name Required")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Address Required")
    private String address;

    private String profilePicture;

}
