package com.meeting.springboot_meet.security.oauth2.user;

import java.util.Map;

public class GithubUserInfo extends OAuth2UserInfo {
    public GithubUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("login");
    } // GitHub dùng 'login'

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }
}
