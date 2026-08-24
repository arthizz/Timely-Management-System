package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CreateUserRequest extends UserRequest{

    @NotBlank(message = "Password Required")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String password;

}
