package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.entities.RevokedAccessToken;
import com.art.timelymanagementsystem.repositories.RevokedAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RevokedAccessTokenService {

    private final RevokedAccessTokenRepository revokedAccessTokenRepository;

    public void revokeAccessToken(String tokenId, LocalDateTime expiresAt){

        RevokedAccessToken revokedAccessToken = new RevokedAccessToken();
        revokedAccessToken.setExpiresAt(expiresAt);
        revokedAccessToken.setTokenId(tokenId);
        revokedAccessTokenRepository.save(revokedAccessToken);

    }

    public boolean isAccessTokenRevoked(String tokenId){

        return revokedAccessTokenRepository.existsByTokenId(tokenId);

    }

}
