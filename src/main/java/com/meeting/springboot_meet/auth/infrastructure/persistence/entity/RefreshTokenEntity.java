package com.meeting.springboot_meet.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    private Instant createdAt;
    private Instant expiryDate;
    private Instant lastUsedAt;
    private String deviceName;
    private String ipAddress;

    @Builder.Default
    @Column(nullable = false)
    private Boolean revoked = false;

    private Instant revokedAt;
    private String replacedByToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
