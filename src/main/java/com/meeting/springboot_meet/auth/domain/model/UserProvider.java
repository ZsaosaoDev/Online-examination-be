package com.meeting.springboot_meet.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserProvider {
    private Long id;
    private ProviderType provider;
    private String providerUserId;
    private String passwordHash; // chỉ dùng cho LOCAL
    private User user;
}