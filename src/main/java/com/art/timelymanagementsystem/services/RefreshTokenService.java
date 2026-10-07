package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateRefreshToken(){

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

    }

    public String hashRefreshToken(String generatedToken){

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] hash = messageDigest.digest(generatedToken.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);

        }catch (NoSuchAlgorithmException e){

            throw new IllegalStateException("SHA-256 algo not available", e);

        }

    }

    public String createRefreshToken(User user){

        String rawToken = generateRefreshToken();

        String hashToken = hashRefreshToken(rawToken);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setTokenHash(hashToken);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);

        return rawToken;

    }

}
