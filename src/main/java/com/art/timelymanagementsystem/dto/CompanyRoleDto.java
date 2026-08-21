package com.art.timelymanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompanyRoleDto {

    private Long id;
    private String roleName;
    private CompanyDto company;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<UserDto> users;

}
