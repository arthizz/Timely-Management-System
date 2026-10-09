package com.art.timelymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@Table(name = "revoked_access_token")
public class RevokedAccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token_id", nullable = false)
    private String tokenId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at", nullable = false)
    private LocalDateTime revokedAt;

    @PrePersist
    public void prePersist(){

        revokedAt = LocalDateTime.now();

    }

}
