package com.meeting.springboot_meet.auth.infrastructure.persistence.entity;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user_providers")
public class UserProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Column(nullable = false)
    private String providerUserId;

    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
