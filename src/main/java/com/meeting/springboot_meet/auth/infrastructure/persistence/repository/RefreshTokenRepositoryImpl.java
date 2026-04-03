package com.meeting.springboot_meet.auth.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.domain.model.RefreshToken;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.repository.RefreshTokenRepository;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.mapper.RefreshTokenMapper;
import com.meeting.springboot_meet.auth.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final SpringDataRefreshTokenRepository springDataRefreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserMapper userMapper;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return springDataRefreshTokenRepository.findByToken(token)
                .map(refreshTokenMapper::toDomain);
    }

    @Override
    public void deleteByToken(String token) {
        springDataRefreshTokenRepository.deleteByToken(token);
    }

    @Override
    public List<RefreshToken> findByUser(User user) {
        return springDataRefreshTokenRepository.findByUser(userMapper.toEntity(user))
                .stream()
                .map(refreshTokenMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = refreshTokenMapper.toEntity(refreshToken);
        RefreshTokenEntity saved = springDataRefreshTokenRepository.save(entity);
        return refreshTokenMapper.toDomain(saved);
    }

    @Override
    public void deleteAll(List<RefreshToken> refreshTokens) {
        List<RefreshTokenEntity> entities = refreshTokens.stream()
                .map(refreshTokenMapper::toEntity)
                .collect(Collectors.toList());
        springDataRefreshTokenRepository.deleteAll(entities);
    }
}
