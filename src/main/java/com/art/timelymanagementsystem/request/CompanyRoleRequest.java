package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyRoleRequest {

    @NotBlank(message = "Role Title cannot be blank or empty")
    private String roleName;

    @NotNull(message = "Company Required")
    private Long companyId;

}
