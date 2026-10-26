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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
//        verify(refreshTokenRepository).save(any(RefreshToken.class));

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());
        RefreshToken savedToken = captor.getValue();

        assertNotNull(loginResponseDto);
        assertEquals("mock-access-token", loginResponseDto.getAccessToken());
        assertNotNull(loginResponseDto.getRefreshToken());
        assertFalse(loginResponseDto.getRefreshToken().isBlank());
        assertTrue(refreshToken.isRevoked());

        assertEquals(user, savedToken.getUser());
        assertFalse(savedToken.isRevoked());
        assertNotNull(savedToken.getTokenHash());
        assertFalse(savedToken.getTokenHash().isBlank());
        assertTrue(savedToken.getExpiresAt().isAfter(LocalDateTime.now()));
    }

    @Test
    void createRefreshToken_shouldReturnToken_whenUserIsValid(){

        User user = new User();
        user.setEmail("test@mail.com");

        String rawToken = refreshTokenService.createRefreshToken(user);

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());
        RefreshToken savedToken = captor.getValue();

        assertNotNull(rawToken);
        assertFalse(rawToken.isBlank());

        assertEquals(user, savedToken.getUser());
        assertNotNull(savedToken.getTokenHash());
        assertNotEquals(savedToken.getTokenHash(), rawToken);
        assertFalse(savedToken.isRevoked());
        assertFalse(savedToken.getTokenHash().isBlank());
        assertTrue(savedToken.getExpiresAt().isAfter(LocalDateTime.now().plusDays(6)));

    }

    @Test
    void hashRefreshToken_shouldReturnSameHash_whenInputIsSame(){

        String rawToken = "test-refresh-token";

        String firstHash = refreshTokenService.hashRefreshToken(rawToken);
        String secondHash = refreshTokenService.hashRefreshToken(rawToken);

        assertNotNull(firstHash);
        assertEquals(secondHash, firstHash);

    }

    @Test
    void hashRefreshToken_shouldReturnDifferentHash_whenInputDiffer(){

        String firstRawToken = "test-refresh-token1";
        String secondRawToken = "test-refresh-token2";

        String firstHash = refreshTokenService.hashRefreshToken(firstRawToken);
        String secondHash = refreshTokenService.hashRefreshToken(secondRawToken);

        assertNotEquals(firstHash, secondHash);

    }

    @Test
    void hashRefreshToken_shouldNotReturnRawToken(){

        String rawToken = "test-refresh-token";

        String hashToken = refreshTokenService.hashRefreshToken(rawToken);

        assertNotEquals(rawToken, hashToken);

    }

    @Test
    void revokeRefreshToken_shouldRevokeAndSave_whenTokenIsValid(){

        String rawToken = "raw-refresh-token";
        String hashToken = refreshTokenService.hashRefreshToken(rawToken);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshToken.setTokenHash(hashToken);

        when(refreshTokenRepository.findByTokenHash(refreshToken.getTokenHash())).thenReturn(Optional.of(refreshToken));

        refreshTokenService.revokeRefreshToken(rawToken);
        assertTrue(refreshToken.isRevoked());
        verify(refreshTokenRepository).save(refreshToken);

    }

    @Test
    void validateRefreshToken_shouldThrowException_whenTokenIsEmpty(){

        String rawToken = "invalid-raw-token";
        String hashToken = refreshTokenService.hashRefreshToken(rawToken);

        when(refreshTokenRepository.findByTokenHash(hashToken)).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> refreshTokenService.revokeRefreshToken(rawToken)
        );

        assertEquals("Refresh token invalid", exception.getMessage());
        verify(refreshTokenRepository, never()).save(any(RefreshToken.class));

    }

}
