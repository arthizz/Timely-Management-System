package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyRequest {

    @NotBlank(message = "Company Name Required")
    private String companyName;

    @NotBlank(message = "Please provide a company address")
    private String companyAddress;

    @Email(message = "Field must be an email")
    @NotBlank(message = "Please provide a valid company email")
    private String companyAdminEmail;

}
