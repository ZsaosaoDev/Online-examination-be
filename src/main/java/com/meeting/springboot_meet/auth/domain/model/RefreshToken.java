package com.meeting.springboot_meet.auth.domain.model;

import lombok.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {
    private Long id;
    private String token;
    private Instant createdAt;
    private Instant expiryDate;
    private Instant lastUsedAt;
    private String deviceName;
    private String ipAddress;
    @Builder.Default
    private Boolean revoked = false;
    private Instant revokedAt;
    private String replacedByToken;
    private User user;
}