package com.meeting.springboot_meet.auth.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.repository.UserProviderRepository;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserProviderEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.mapper.UserProviderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProviderRepositoryImpl implements UserProviderRepository {

    private final SpringDataUserProviderRepository springDataUserProviderRepository;
    private final UserProviderMapper userProviderMapper;

    @Override
    public Optional<UserProvider> findByProviderAndProviderUserId(ProviderType provider, String providerUserId) {
        return springDataUserProviderRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .map(userProviderMapper::toDomain);
    }

    @Override
    public boolean existsByUserIdAndProvider(Long userId, ProviderType providerType) {
        return springDataUserProviderRepository.existsByUserIdAndProvider(userId, providerType);
    }

    @Override
    public UserProvider save(UserProvider userProvider) {
        UserProviderEntity entity = userProviderMapper.toEntity(userProvider);
        UserProviderEntity saved = springDataUserProviderRepository.save(entity);
        return userProviderMapper.toDomain(saved);
    }
}
