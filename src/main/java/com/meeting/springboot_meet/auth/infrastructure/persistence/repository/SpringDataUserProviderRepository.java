package com.meeting.springboot_meet.auth.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserProviderRepository extends JpaRepository<UserProviderEntity, Long> {
    Optional<UserProviderEntity> findByProviderAndProviderUserId(ProviderType provider, String providerUserId);
    boolean existsByUserIdAndProvider(Long userId, ProviderType providerType);
}
