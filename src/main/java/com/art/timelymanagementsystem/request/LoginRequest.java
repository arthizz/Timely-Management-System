package com.art.timelymanagementsystem.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Please provide an email")
    @Email(message = "must be a valid email format")
    private String email;

    @NotBlank(message = "Please provide a password")
    private String password;

}
