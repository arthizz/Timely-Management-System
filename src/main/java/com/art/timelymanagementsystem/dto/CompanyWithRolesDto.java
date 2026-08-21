package com.art.timelymanagementsystem.dto;

import com.art.timelymanagementsystem.entities.CompanyRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CompanyWithRolesDto {

    private int id;
    private String companyName;
    private String companyAddress;
    private String companyAdminEmail;
    private LocalDateTime createdAt;
    private List<CompanyRoleDto> companyRoles;

}
