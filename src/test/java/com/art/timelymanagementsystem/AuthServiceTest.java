package com.art.timelymanagementsystem;

import com.art.timelymanagementsystem.security.JwtService;
import com.art.timelymanagementsystem.services.AuthService;
import com.art.timelymanagementsystem.services.RefreshTokenService;
import com.art.timelymanagementsystem.services.RevokedAccessTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private RevokedAccessTokenService revokedAccessTokenService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void logout_shouldRevokedRefreshToken_whenAuthorizationHeaderIsMissing(){

        String rawRefreshToken = "test-refresh-token";

        authService.LogoutService(null, rawRefreshToken);

        verify(refreshTokenService).revokeRefreshToken(rawRefreshToken);

        verifyNoInteractions(jwtService);
        verifyNoInteractions(revokedAccessTokenService);

    }

    @Test
    void logout_shouldRevokeAccessTokenAndRefreshTokens_whenTokensAreValid(){

        String accessToken = "valid-access-token";
        String authHeader = "Bearer " + accessToken;
        String rawRefreshToken = "valid-refresh-token";
        String tokenId = "jti-123";

        Claims claims = mock(Claims.class);

        Date expiration = Date.from(
                Instant.now().plusSeconds(600)
        );

        when(jwtService.extractClaimsForLogout(accessToken))
                .thenReturn(claims);

        when(claims.getId()).thenReturn(tokenId);

        when(claims.getExpiration()).thenReturn(expiration);

        when(revokedAccessTokenService.isAccessTokenRevoked(tokenId))
                .thenReturn(false);

        authService.LogoutService(authHeader, rawRefreshToken);

        verify(jwtService).extractClaimsForLogout(accessToken);

        verify(revokedAccessTokenService)
                .revokeAccessToken(eq(tokenId), any(LocalDateTime.class));

        verify(refreshTokenService).revokeRefreshToken(rawRefreshToken);

    }

    @Test
    void logout_shouldNotRevokedAccessTokenAgain_whenAlreadyRevoked(){

        String accessToken = "valid-access-token";
        String authHeader = "Bearer " + accessToken;
        String rawRefreshToken = "valid-refres-token";
        String tokenId = "jti-123";

        Claims claims = mock(Claims.class);

        Date expiration = Date.from(
                Instant.now().plusSeconds(600)
        );

        when(jwtService.extractClaimsForLogout(accessToken))
                .thenReturn(claims);

        when(claims.getId()).thenReturn(tokenId);

        when(claims.getExpiration()).thenReturn(expiration);

        when(revokedAccessTokenService.isAccessTokenRevoked(tokenId))
                .thenReturn(true);

        authService.LogoutService(authHeader, rawRefreshToken);

        verify(revokedAccessTokenService).isAccessTokenRevoked(tokenId);
        verify(revokedAccessTokenService, never())
                .revokeAccessToken(anyString(), any(LocalDateTime.class));

        verify(refreshTokenService).revokeRefreshToken(rawRefreshToken);

    }

    @Test
    void logout_shouldRevokedRefreshToken_whenAccessTokenIsInvalid(){

        String accessToken = "invalid-Access-token";
        String authHeader = "Bearer " + accessToken;
        String refreshToken = "valid-refresh-token";

        when(jwtService.extractClaimsForLogout(accessToken))
                .thenThrow(new MalformedJwtException("Invalid Access Token"));

        authService.LogoutService(authHeader, refreshToken);

        verify(revokedAccessTokenService, never()).revokeAccessToken(anyString(), any(LocalDateTime.class));
        verify(revokedAccessTokenService, never()).isAccessTokenRevoked(anyString());

        verify(refreshTokenService).revokeRefreshToken(refreshToken);

    }

}
