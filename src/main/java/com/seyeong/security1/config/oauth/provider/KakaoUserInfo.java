package com.seyeong.security1.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes; // oauth2User.getattributes()
    private Map<String, Object> properties;
    private Map<String, Object> account;


    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map) attributes.get("properties");
        this.account = (Map) attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) account.get("email");
    }

    @Override
    public String getName() {
        return (String) properties.get("nickname");
    }

}
