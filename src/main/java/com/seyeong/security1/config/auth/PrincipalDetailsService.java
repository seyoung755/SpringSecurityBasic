package com.seyeong.security1.config.auth;

import com.seyeong.security1.model.User;
import com.seyeong.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessionUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 session <= Authentication <= UserDetails
    // 시큐리티 session (내부 Authentication (내부 UserDetails))
    // 해당 함수가 종료될 때 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : "+ username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity!=null) {
            return new PrincipalDetails(userEntity); // 리턴될 때 자동으로 Authenticaion 객체에 담아 시큐리티 session에 넣어줌.
        }
        return null;
    }
}
