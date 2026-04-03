package com.meeting.springboot_meet.auth.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.auth.domain.model.RefreshToken;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    private final UserMapper userMapper;

    public RefreshTokenMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) return null;
        return RefreshToken.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .createdAt(entity.getCreatedAt())
                .expiryDate(entity.getExpiryDate())
                .lastUsedAt(entity.getLastUsedAt())
                .deviceName(entity.getDeviceName())
                .ipAddress(entity.getIpAddress())
                .revoked(entity.getRevoked())
                .revokedAt(entity.getRevokedAt())
                .replacedByToken(entity.getReplacedByToken())
                .user(userMapper.toDomain(entity.getUser()))
                .build();
    }

    public RefreshTokenEntity toEntity(RefreshToken domain) {
        if (domain == null) return null;
        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .createdAt(domain.getCreatedAt())
                .expiryDate(domain.getExpiryDate())
                .lastUsedAt(domain.getLastUsedAt())
                .deviceName(domain.getDeviceName())
                .ipAddress(domain.getIpAddress())
                .revoked(domain.getRevoked())
                .revokedAt(domain.getRevokedAt())
                .replacedByToken(domain.getReplacedByToken())
                .user(userMapper.toEntity(domain.getUser()))
                .build();
    }
}
