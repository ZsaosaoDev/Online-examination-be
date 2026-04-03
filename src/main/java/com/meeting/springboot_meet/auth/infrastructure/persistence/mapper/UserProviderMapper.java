package com.meeting.springboot_meet.auth.infrastructure.persistence.mapper;

import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProviderMapper {

    private final UserMapper userMapper;

    public UserProviderMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserProvider toDomain(UserProviderEntity entity) {
        if (entity == null) return null;
        return UserProvider.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .providerUserId(entity.getProviderUserId())
                .passwordHash(entity.getPasswordHash())
                .user(userMapper.toDomain(entity.getUser()))
                .build();
    }

    public UserProviderEntity toEntity(UserProvider domain) {
        if (domain == null) return null;
        return UserProviderEntity.builder()
                .id(domain.getId())
                .provider(domain.getProvider())
                .providerUserId(domain.getProviderUserId())
                .passwordHash(domain.getPasswordHash())
                .user(userMapper.toEntity(domain.getUser()))
                .build();
    }
}
