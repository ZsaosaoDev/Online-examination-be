package com.meeting.springboot_meet.security.oauth2.user;

import java.util.Map;

public class FacebookUserInfo extends OAuth2UserInfo {
    public FacebookUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }
}