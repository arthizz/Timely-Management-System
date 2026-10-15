package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.RefreshToken;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
     SELECT rt FROM RefreshToken rt WHERE rt.tokenHash = :tokenHash
    """)
    Optional<RefreshToken> findByTokenHashForUpdate(@Param("tokenHash") String tokenHash);

    @Modifying
    @Query("""
     DELETE FROM RefreshToken rt
     WHERE rt.expiresAt <= :dateNow
    """)
    int deleteExpiredToken(@Param("dateNow") LocalDateTime dateNow);

}
