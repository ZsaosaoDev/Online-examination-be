package com.meeting.springboot_meet.security.oauth2;

import com.meeting.springboot_meet.auth.application.service.OAuth2Service;
import com.meeting.springboot_meet.auth.application.service.TokenService;
import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.presentation.payload.LoginResult;
import com.meeting.springboot_meet.auth.infrastructure.security.CookieUtil;
import com.meeting.springboot_meet.security.oauth2.user.OAuth2UserInfo;
import com.meeting.springboot_meet.security.oauth2.user.OAuth2UserInfoFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2Service oAuth2Service;
    private final TokenService tokenService;
    private final CookieUtil cookieUtil;

    @Value("${frontend.callback-url}")
    private String frontendCallbackUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String registrationId = ((OAuth2AuthenticationToken) authentication)
                .getAuthorizedClientRegistrationId();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId,
                oauth2User.getAttributes()
        );

        User user = oAuth2Service.loginWithOAuth2(
                userInfo.getEmail(),
                userInfo.getName(),
                registrationId
        );

        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();

        LoginResult loginResult = tokenService.generateTokens(user, userAgent, ip);

        cookieUtil.addRefreshTokenCookie(response, loginResult.getRefreshToken());

        // 5. Redirect về Frontend
        String targetUrl = UriComponentsBuilder.fromUriString(frontendCallbackUrl)
                .queryParam("accessToken", loginResult.getAuthResponse().getAccessToken())
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}



