package com.art.timelymanagementsystem.repositories;


import com.art.timelymanagementsystem.entities.RevokedAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RevokedAccessTokenRepository extends JpaRepository<RevokedAccessToken, Long> {

    boolean existsByTokenId(String tokenId);

    @Modifying
    @Query("""
        DELETE FROM RevokedAccessToken rat
        WHERE rat.expiresAt <= :dateNow
    """)
    int deleteExpiredAccessToken(@Param("dateNow")LocalDateTime dateNow);

}
