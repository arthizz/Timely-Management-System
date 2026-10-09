package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.LoginResponseDto;
import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.repositories.RefreshTokenRepository;
import com.art.timelymanagementsystem.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final JwtService jwtService;

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

    public RefreshToken validateRefreshToken(String rawToken){

        String tokenHash = hashRefreshToken(rawToken);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash).orElseThrow(() -> new BadRequestException("Refresh token invalid"));

        if(refreshToken.isRevoked()){

            throw new BadRequestException("Please login again to generate a new one");

        }

        if(refreshToken.getExpiresAt().isBefore(LocalDateTime.now())){

            throw new BadRequestException("Refresh token expired");

        }

        return refreshToken;

    }

    public LoginResponseDto refreshAccessToken(RefreshToken refreshToken, String rawToken){

        User user = refreshToken.getUser();

        String jwt = jwtService.generateJwt(user.getEmail());

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        loginResponseDto.setRefreshToken(rawToken);
        loginResponseDto.setAccessToken(jwt);

        return loginResponseDto;

    }

    public void revokeRefreshToken(String rawToken){

        RefreshToken refreshToken = validateRefreshToken(rawToken);
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

    }

}
