package com.i5e2.likeawesomevegetable.security;

import com.i5e2.likeawesomevegetable.user.basic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.token.secret}")
    private String secretKey;
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final RedisAccessTokenUtil redisAccessTokenUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용하는 경우 사용
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/test-security-ba").hasAnyRole("BASIC", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/test-security-ad").hasRole("ADMIN")
                /*.antMatchers(HttpMethod.GET, "/api/**").authenticated()*/
                .antMatchers("/**").permitAll()
//                .antMatchers("/**").permitAll()
                /*.antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll() // join,login은 언제나 가능
                .antMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()*/
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 토큰 없는 경우 예외처리
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // role 권한 관련 예외처리
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService) // provider로부터 받은 유저정보를 다룰 service
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler) // oauth 로그인 성공했을 때 호출 handler
                .and()
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, redisAccessTokenUtil), UsernamePasswordAuthenticationFilter.class)
                //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용
                .addFilterBefore(new JwtTokenExceptionFilter(), JwtTokenFilter.class)
                .build();
    }
}
