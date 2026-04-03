package com.meeting.springboot_meet.security.oauth2.user;

import com.meeting.springboot_meet.auth.domain.model.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());

        return switch (providerType) {
            case GOOGLE -> new GoogleUserInfo(attributes);
            case FACEBOOK -> new FacebookUserInfo(attributes);
            case GITHUB -> new GithubUserInfo(attributes);
            case LOCAL -> throw new IllegalArgumentException("Local login không sử dụng OAuth2UserInfo");
            default -> throw new IllegalArgumentException("Provider không được hỗ trợ: " + registrationId);
        };
    }
}
