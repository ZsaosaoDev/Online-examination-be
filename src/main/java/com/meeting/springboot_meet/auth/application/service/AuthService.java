package com.meeting.springboot_meet.auth.application.service;

import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.repository.UserRepository;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import com.meeting.springboot_meet.auth.presentation.payload.LoginRequest;
import com.meeting.springboot_meet.auth.presentation.payload.RegisterRequest;
import com.meeting.springboot_meet.auth.domain.exception.AuthException;
import com.meeting.springboot_meet.auth.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserProviderService userProviderService;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RedisVerificationService redisVerificationService;
    private final EmailService emailService;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        String token = UUID.randomUUID().toString();

        redisVerificationService.saveVerification(token,
                request.getEmail(),
                hashedPassword);

        emailService.sendVerificationEmail(request.getEmail(), token);
    }

    @Transactional
    public void verifyEmailToken(String token) {
        String value = redisVerificationService.getVerification(token);
        if (value == null) {
            throw new RuntimeException("Token không hợp lệ hoặc hết hạn");
        }

        String[] parts = value.split(":");
        String email = parts[0];
        String hashedPassword = parts[1];

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Tài khoản đã tồn tại hoặc đã được kích hoạt");
        }

        User user = userRepository.save(
                User.builder()
                        .email(email)
                        .enabled(true)
                        .build()
        );

        userProviderService.createLocalProvider(user, email, hashedPassword);
        
        redisVerificationService.deleteVerification(token);
    }


    public LoginResult login(LoginRequest request, String userAgent, String ip) {
        User user = authenticationService.authenticateLocal(
                request.getEmail(),
                request.getPassword()
        );
        return tokenService.generateTokens(user, userAgent, ip);
    }

    public LoginResult refreshAccessToken(String oldToken, String userAgent, String ip) {
        return tokenService.refreshAccessToken(oldToken, userAgent, ip);
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenService.revokeByToken(refreshToken);
    }
}


