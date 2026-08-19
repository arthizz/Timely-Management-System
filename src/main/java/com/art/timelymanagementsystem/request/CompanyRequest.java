package com.art.timelymanagementsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyRequest {

    private String companyName;
    private String companyAddress;
    private String companyAdminEmail;
    private Boolean isVerified;
    private Boolean isSubscribed;

}
