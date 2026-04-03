package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import com.meeting.springboot_meet.auth.domain.exception.ErrorCode;
import com.meeting.springboot_meet.auth.domain.model.RefreshToken;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.token.max-age}")
    private long refreshTokenMaxAge;

    @Transactional
    public void createRefreshToken(User user, String refreshToken, String userAgent, String ip) {
        Instant now = Instant.now();
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .createdAt(now)
                .lastUsedAt(now)
                .expiryDate(now.plusSeconds(refreshTokenMaxAge))
                .deviceName(userAgent)
                .ipAddress(ip)
                .build();

        refreshTokenRepository.save(token);
    }

    @Transactional
    public RefreshToken verifyToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new AuthException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new AuthException(ErrorCode.TOKEN_EXPIRED);
        }

        refreshToken.setLastUsedAt(Instant.now());
        return refreshToken;
    }

    @Transactional
    public void revokeByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new AuthException(
                        ErrorCode.INVALID_REFRESH_TOKEN
                ));

        refreshToken.setRevoked(true);
    }

    @Transactional
    public void revokeAllByUser(User user) {
        var tokens = refreshTokenRepository.findByUser(user);
        Instant now = Instant.now();

        for (RefreshToken token : tokens) {
            token.setRevoked(true);
            token.setRevokedAt(now);
        }
    }
}

