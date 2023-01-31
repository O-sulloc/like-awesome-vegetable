package com.i5e2.likeawesomevegetable.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
        writeTokenResponse(response, authentication);
    }
    private void writeTokenResponse(HttpServletResponse response, Authentication authentication) throws IOException {
        log.info(authentication.getPrincipal().toString());

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

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(),
                new OAuthLoginResponse(token));
    }
}
