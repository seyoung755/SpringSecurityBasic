package com.seyeong.security1.config.oauth;

import com.seyeong.security1.config.auth.PrincipalDetails;
import com.seyeong.security1.model.User;
import com.seyeong.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// 해당 함수가 종료될 때 @AuthenticationPrincipal 어노테이션이 만들어진다.

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : " + userRequest.getClientRegistration());
        System.out.println("userRequest : " + userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("userRequest : " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider+"_"+providerId;
        String email = oAuth2User.getAttribute("email");
        String password = encoder.encode("세영");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .password(password)
                    .role(role)
                    .build();
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
