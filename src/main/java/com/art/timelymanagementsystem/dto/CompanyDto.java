package com.art.timelymanagementsystem.dto;

import java.time.LocalDateTime;

public class CompanyDto {

    private int id;
    private String companyName;
    private String companyAddress;
    private String companyAdminEmail;
    private Boolean isVerified;
    private Boolean isSubscribed;
    private LocalDateTime createdAt;

}
