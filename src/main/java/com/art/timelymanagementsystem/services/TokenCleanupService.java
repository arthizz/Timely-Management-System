package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.repositories.RefreshTokenRepository;
import com.art.timelymanagementsystem.repositories.RevokedAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RevokedAccessTokenRepository revokedAccessTokenRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cleanupExpiredToken(){

        LocalDateTime currentDate = LocalDateTime.now();

        int deletedRefreshToken = refreshTokenRepository.deleteExpiredToken(currentDate);
        int deletedAccessToken = revokedAccessTokenRepository.deleteExpiredAccessToken(currentDate);

        log.info("Deleted Refresh Token: {}", deletedRefreshToken);
        log.info("Deleted Access Token: {}", deletedAccessToken);

    }

}
