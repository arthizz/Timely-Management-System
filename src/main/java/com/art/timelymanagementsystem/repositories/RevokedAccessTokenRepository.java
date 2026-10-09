package com.art.timelymanagementsystem.repositories;


import com.art.timelymanagementsystem.entities.RevokedAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedAccessTokenRepository extends JpaRepository<RevokedAccessToken, Long> {

    boolean existsByTokenId(String tokenId);

}
