package com.meeting.springboot_meet.auth.presentation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResult {
    private AuthResponse authResponse;
    private String refreshToken;
}
