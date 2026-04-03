package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.repository.UserProviderRepository;
import com.meeting.springboot_meet.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProviderService {

    private final UserProviderRepository userProviderRepository;
    private final PasswordEncoder passwordEncoder;

    public void createLocalProvider(User user, String email, String rawPassword) {
        UserProvider provider = UserProvider.builder()
                .provider(ProviderType.LOCAL)
                .providerUserId(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .user(user)
                .build();

        userProviderRepository.save(provider);
    }
}


