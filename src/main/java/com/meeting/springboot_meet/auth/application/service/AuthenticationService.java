package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import com.meeting.springboot_meet.auth.domain.model.UserProvider;
import com.meeting.springboot_meet.auth.domain.repository.UserProviderRepository;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import com.meeting.springboot_meet.auth.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserProviderRepository userProviderRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticateLocal(String email, String rawPassword) {
        UserProvider provider = userProviderRepository
                .findByProviderAndProviderUserId(
                        ProviderType.LOCAL,
                        email
                )
                .orElseThrow(() -> new AuthException(
                        ErrorCode.INVALID_CREDENTIALS
                ));
        if (!passwordEncoder.matches(rawPassword, provider.getPasswordHash())) {
            throw new AuthException(
                    ErrorCode.INVALID_CREDENTIALS
            );
        }
        return provider.getUser();
    }
}


