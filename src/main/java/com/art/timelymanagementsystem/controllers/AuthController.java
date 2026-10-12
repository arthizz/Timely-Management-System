package com.art.timelymanagementsystem.controllers;


import com.art.timelymanagementsystem.dto.LoginResponseDto;
import com.art.timelymanagementsystem.dto.LogoutResponseDto;
import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.request.LoginRequest;
import com.art.timelymanagementsystem.request.RefreshTokenRequest;
import com.art.timelymanagementsystem.security.JwtService;
import com.art.timelymanagementsystem.services.RefreshTokenService;
import com.art.timelymanagementsystem.services.RevokedAccessTokenService;
import com.art.timelymanagementsystem.services.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthenticationService userAuthenticationService;
    private final RefreshTokenService refreshTokenService;
    private final RevokedAccessTokenService revokedAccessTokenService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(userAuthenticationService.loginService(request));

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshAuthToken(@Valid @RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(refreshTokenService.refreshAccessToken(refreshToken, request.getRefreshToken()));

    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody RefreshTokenRequest request){

        refreshTokenService.revokeRefreshToken(request.getRefreshToken());

        LogoutResponseDto logoutResponseDto = new LogoutResponseDto();
        logoutResponseDto.setMessage("Logout Success");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Missing or invalid Authorization header");

        }

        String accessToken = authHeader.substring(7);
        String tokenId = jwtService.extractTokenId(accessToken);
        LocalDateTime expiresAt = jwtService.extractExpiration(accessToken);

        revokedAccessTokenService.revokeAccessToken(tokenId, expiresAt);


        return ResponseEntity.ok(logoutResponseDto);

    }

}
