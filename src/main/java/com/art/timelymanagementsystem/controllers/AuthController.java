package com.art.timelymanagementsystem.controllers;


import com.art.timelymanagementsystem.dto.LoginResponseDto;
import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.request.LoginRequest;
import com.art.timelymanagementsystem.request.RefreshTokenRequest;
import com.art.timelymanagementsystem.services.RefreshTokenService;
import com.art.timelymanagementsystem.services.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthenticationService userAuthenticationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(userAuthenticationService.loginService(request));

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshAuthToken(@Valid @RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(refreshTokenService.refreshAccessToken(refreshToken, request.getRefreshToken()));

    }

}
