package com.art.timelymanagementsystem.request;

import com.art.timelymanagementsystem.entities.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class UserRequest {

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

    @DecimalMin(value = "0.00", message = "Hourly Rate cannot be negative or lower than zero")
    private BigDecimal hourlyRate;

    private Long roleId;

    private Long companyId;

    private String profilePicture;

}
