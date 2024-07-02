package com.example.questionapp.services;


import com.example.questionapp.entities.RefreshToken;
import com.example.questionapp.entities.User;
import com.example.questionapp.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public boolean isRefreshExpired(RefreshToken token){
        return token.getExpyrDate().before(new Date());
    }

    public String createRefreshToken(User user){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
        if(refreshToken == null) {
            refreshToken =	new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setExpyrDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
