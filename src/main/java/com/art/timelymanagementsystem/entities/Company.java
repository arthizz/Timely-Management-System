package com.art.timelymanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_admin_email")
    private String companyAdminEmail;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
