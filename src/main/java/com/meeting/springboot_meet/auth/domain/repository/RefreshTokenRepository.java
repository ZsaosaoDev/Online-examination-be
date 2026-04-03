package com.meeting.springboot_meet.auth.domain.repository;

import com.meeting.springboot_meet.auth.domain.model.RefreshToken;
import com.meeting.springboot_meet.auth.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    List<RefreshToken> findByUser(User user);
    RefreshToken save(RefreshToken refreshToken);
    void deleteAll(List<RefreshToken> refreshTokens);
}