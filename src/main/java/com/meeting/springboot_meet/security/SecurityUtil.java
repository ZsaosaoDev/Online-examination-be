package com.meeting.springboot_meet.security;

import com.meeting.springboot_meet.security.service.UserDetailsImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    private SecurityUtil() {
    }

    public static Optional<Long> getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth != null && auth.isAuthenticated()) ||
                auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetailsImpl user) {
            return Optional.ofNullable(user.getId());
        }

        return Optional.empty();
    }

    public static Long getRequiredCurrentUserId() {
        return getCurrentUserId()
                .orElseThrow(() -> new RuntimeException("User not authenticated or session expired"));
    }
}