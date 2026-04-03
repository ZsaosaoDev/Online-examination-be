package com.meeting.springboot_meet.auth.presentation.controller;

import com.meeting.springboot_meet.auth.application.service.AuthService;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import com.meeting.springboot_meet.auth.presentation.payload.LoginRequest;
import com.meeting.springboot_meet.auth.presentation.payload.RegisterRequest;
import com.meeting.springboot_meet.auth.presentation.payload.AuthResponse;
import com.meeting.springboot_meet.auth.infrastructure.security.CookieUtil;
import com.meeting.springboot_meet.common.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>("Register successful", null));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        authService.verifyEmailToken(token);
        return ResponseEntity.ok(new ApiResponse<>("Xác thực thành công, tài khoản đã được kích hoạt", null));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            @RequestHeader(value = "User-Agent", defaultValue = "Unknown") String userAgent,
            HttpServletRequest requestHttp,
            HttpServletResponse response) {

        String ip = requestHttp.getRemoteAddr();

        LoginResult result = authService.login(request, userAgent, ip);

        cookieUtil.addRefreshTokenCookie(response, result.getRefreshToken());
        return ResponseEntity.ok(
                new ApiResponse<>("Login successful", result.getAuthResponse())
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestHeader(value = "User-Agent", defaultValue = "Unknown") String userAgent,
            HttpServletRequest requestHttp,
            HttpServletResponse response) {

        String ip = requestHttp.getRemoteAddr();

        LoginResult result = authService.refreshAccessToken(refreshToken, userAgent, ip);

        cookieUtil.addRefreshTokenCookie(response, result.getRefreshToken());
        return ResponseEntity.ok(
                new ApiResponse<>("Refresh token successful", result.getAuthResponse())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {

        authService.logout(refreshToken);

        ResponseCookie cookie = cookieUtil.clearRefreshTokenCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new ApiResponse<>("Logout successful", null));
    }


}


