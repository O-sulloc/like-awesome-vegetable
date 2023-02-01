package com.i5e2.likeawesomevegetable.security;

import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserType;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserJpaRepository userJpaRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.info("oAuth2User : {}", oAuth2User.toString());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("attribue : {}", attributes.toString());
        Object emails = attributes.get("email");
        log.info("emails : {}", emails.toString());
        String email = String.valueOf(emails);
        saveOrUpdate(email);
        return oAuth2User;
    }

    private void saveOrUpdate(String email) {
        boolean createUserFlag = true;

        Optional<User> user = userJpaRepository.findByEmail(email);
        if (user.isPresent()){
            createUserFlag = false;
            User savedUser = user.get();
            userJpaRepository.save(savedUser);
        }

        if (createUserFlag) {
            userJpaRepository.save(
                    User.builder()
                            .email(email)
                            .userType(UserType.ROLE_BASIC)
                            .build()
            );
        }
    }
}
