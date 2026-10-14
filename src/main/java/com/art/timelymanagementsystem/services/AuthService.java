package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final RevokedAccessTokenService revokedAccessTokenService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void LogoutService(String authHeader, String rawRefreshToken){

        if(authHeader != null && authHeader.startsWith("Bearer ")){

            String accessToken = authHeader.substring(7);
            Claims claims = null;

            try {
                claims = jwtService.extractClaimsForLogout(accessToken);
            }catch (JwtException | IllegalArgumentException e){


            }

            if(claims != null){

                String tokenId = claims.getId();
                Date expiration = claims.getExpiration();

                if(tokenId != null && expiration != null && expiration.after(new Date())){

                    LocalDateTime expiresAt = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    if(!revokedAccessTokenService.isAccessTokenRevoked(tokenId)){

                        revokedAccessTokenService.revokeAccessToken(tokenId, expiresAt);

                    }

                }

            }

        }

        refreshTokenService.revokeRefreshToken(rawRefreshToken);

    }


}
