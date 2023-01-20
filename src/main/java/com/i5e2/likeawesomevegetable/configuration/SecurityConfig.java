package com.i5e2.likeawesomevegetable.configuration;

import lombok.RequiredArgsConstructor;
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                /*.antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll() // join,login은 언제나 가능
                .antMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()*/
                .and()
                .exceptionHandling()
                /*.authenticationEntryPoint(new CustomAuthenticationEntryPointHandler()) // 토큰 없는 경우 예외처리*/
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 사용
                .and()
                /*.addFilterBefore(new JwtTokenFilter(userService, secretKey),
                        UsernamePasswordAuthenticationFilter.class)
                        //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용
                .addFilterBefore(new JwtTokenExceptionFilter(), JwtTokenFilter.class)*/
                .build();
    }
}
