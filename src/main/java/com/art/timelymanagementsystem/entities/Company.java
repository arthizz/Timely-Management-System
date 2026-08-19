package com.art.timelymanagementsystem.entities;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_address", nullable = false)
    private String companyAddress;

    @Column(name = "company_admin_email", nullable = false)
    private String companyAdminEmail;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "is_subscribed", nullable = false)
    private Boolean isSubscribed;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){

        createdAt = LocalDateTime.now();

    }

}
