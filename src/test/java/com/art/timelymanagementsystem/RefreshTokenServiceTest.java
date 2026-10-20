package com.art.timelymanagementsystem;

import com.art.timelymanagementsystem.dto.LoginResponseDto;
import com.art.timelymanagementsystem.entities.RefreshToken;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.repositories.RefreshTokenRepository;
import com.art.timelymanagementsystem.security.JwtService;
import com.art.timelymanagementsystem.services.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtService jwtService;

    @Test
    void validateRefreshToken_shouldThrowException_whenTokenDoesNotExist(){

        when(refreshTokenRepository.findByTokenHash(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.validateRefreshToken("invalid-refresh-token")
        );

    }

    @Test
    void validateRefreshToken_shouldThrowException_whenTokenIsRevoked(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(true);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));

        when(refreshTokenRepository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(refreshToken));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.validateRefreshToken("invalid-refresh-token")
        );

        assertEquals(
                "Please login again to generate a new one",
                exception.getMessage()
        );

    }

    @Test
    void validateRefreshToken_shouldThrowException_whenTokenIsExpired(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(LocalDateTime.now().minusDays(1));

        when(refreshTokenRepository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(refreshToken));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.validateRefreshToken("invalid-refresh-token")
        );

        assertEquals(
                "Refresh token expired",
                exception.getMessage()
        );

    }

    @Test
    void validateRefreshToken_shouldReturnToken_whenTokenIsValid(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));

        when(refreshTokenRepository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(refreshToken));

        assertSame(refreshToken, refreshTokenService.validateRefreshToken("valid-refresh-token"));

    }

    @Test
    void rotateRefreshToken_shouldThrowException_whenTokenDoesNotExist(){

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.rotateRefreshToken("invalid-refresh-token")
        );

        assertEquals("Refresh token invalid", exception.getMessage());

    }

    @Test
    void rotateRefreshToken_shouldThrowException_whenTokenIsRevoked(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(true);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(refreshToken));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.rotateRefreshToken("invalid-refresh-token")
        );

        assertEquals("Please login again to generate a new token", exception.getMessage());

    }

    @Test
    void rotateRefreshToken_shouldThrowException_whenTokenIsExpired(){

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(LocalDateTime.now().minusDays(1));

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(refreshToken));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.rotateRefreshToken("invalid-refresh-token")
        );

        assertEquals("Refresh token expired", exception.getMessage());

    }

    @Test
    void rotateRefreshToken_shouldReturnNewToken_whenTokenIsValid(){

        User user = new User();
        user.setEmail("test@mail.com");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshToken.setUser(user);

        when(refreshTokenRepository.findByTokenHashForUpdate(anyString()))
                .thenReturn(Optional.of(refreshToken));

        when(jwtService.generateJwt(user.getEmail()))
                .thenReturn("mock-access-token");

        LoginResponseDto loginResponseDto = refreshTokenService.rotateRefreshToken("valid-refresh-token");

        assertNotNull(loginResponseDto);
        assertEquals("mock-access-token", loginResponseDto.getAccessToken());
        assertNotNull(loginResponseDto.getRefreshToken());
        assertFalse(loginResponseDto.getRefreshToken().isBlank());
        assertTrue(refreshToken.isRevoked());
    }

}
