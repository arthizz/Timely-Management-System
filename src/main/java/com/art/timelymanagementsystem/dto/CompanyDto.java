package com.art.timelymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private int id;
    private String companyName;
    private String companyAddress;
    private String companyAdminEmail;
    private Boolean isVerified;
    private Boolean isSubscribed;
    private LocalDateTime createdAt;

}
