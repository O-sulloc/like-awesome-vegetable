package com.i5e2.likeawesomevegetable.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.token.secret}") // Spring Annotation
    private String secretKey;

    private long expireTimeMs = 10000 * 60 * 60 ; // 1시간

    private final RedisAccessTokenUtil redisAccessTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        /* 로그인 하기 전의 페이지의 url을 담아서 로그인 이후 redirect 주소로 설정 (refactoring 해보기) */

        if(response.isCommitted()){
            log.debug("Response has already been commited");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String targetUrl = getDefaultTargetUrl();
//        String targetUrl = "http://localhost:8080/oauth2/redirect";
        log.debug(targetUrl);

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Object emails = attributes.get("email");
        String email = String.valueOf(emails);
        String token = JwtTokenUtil.createToken(email,secretKey,expireTimeMs);

        if (redisAccessTokenUtil.hasAccessToken(email)) {
            redisAccessTokenUtil.saveBlockAccessToken(
                    redisAccessTokenUtil.getAccessToken(email)
            );
        }
        redisAccessTokenUtil.saveAccessToken(email, token);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", token)
                .build().toUriString();
    }

}
