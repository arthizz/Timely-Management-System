package com.art.timelymanagementsystem.controllers;


import com.art.timelymanagementsystem.dto.LoginResponseDto;
import com.art.timelymanagementsystem.dto.LogoutResponseDto;
import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.request.LoginRequest;
import com.art.timelymanagementsystem.request.RefreshTokenRequest;
import com.art.timelymanagementsystem.security.JwtService;
import com.art.timelymanagementsystem.services.AuthService;
import com.art.timelymanagementsystem.services.RefreshTokenService;
import com.art.timelymanagementsystem.services.RevokedAccessTokenService;
import com.art.timelymanagementsystem.services.UserAuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthenticationService userAuthenticationService;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(userAuthenticationService.loginService(request));

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshAuthToken(@Valid @RequestBody RefreshTokenRequest request){

//        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());

//        return ResponseEntity.ok(refreshTokenService.refreshAccessToken(refreshToken, request.getRefreshToken()));

        return ResponseEntity.ok(refreshTokenService.rotateRefreshToken(request.getRefreshToken()));

    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody RefreshTokenRequest request){

        authService.LogoutService(authHeader, request.getRefreshToken());

        LogoutResponseDto logoutResponseDto = new LogoutResponseDto();
        logoutResponseDto.setMessage("Logout Success");

        return ResponseEntity.ok(logoutResponseDto);

    }

}
