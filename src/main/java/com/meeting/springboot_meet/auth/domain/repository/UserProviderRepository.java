package com.meeting.springboot_meet.auth.domain.repository;

import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import java.util.Optional;

public interface UserProviderRepository {
    Optional<UserProvider> findByProviderAndProviderUserId(ProviderType provider, String providerUserId);
    boolean existsByUserIdAndProvider(Long userId, ProviderType providerType);
    UserProvider save(UserProvider userProvider);
}
