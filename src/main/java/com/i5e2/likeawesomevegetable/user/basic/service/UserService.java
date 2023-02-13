package com.i5e2.likeawesomevegetable.user.basic.service;

import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.security.JwtTokenUtil;
import com.i5e2.likeawesomevegetable.security.RedisAccessTokenUtil;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.dto.*;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserJpaRepository userJpaRepository;

    private final RedisAccessTokenUtil redisAccessTokenUtil;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expireTimeMs = 1000 * 60 * 60; // 1h

    public UserJoinResponse join(UserJoinRequest dto) {
        if (!isNotEmailExist(dto.getEmail())) {
            throw new AwesomeVegeAppException(AppErrorCode.DUPLICATED_EMAIL,
                    AppErrorCode.DUPLICATED_EMAIL.getMessage());
        }
        User savedUser = userJpaRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .managerName(dto.getManagerName())
                        .manaverPhoneNo(dto.getPhoneNo())
                        .password(encoder.encode(dto.getPassword()))
                        .userType(UserType.ROLE_BASIC)
                        .build()
        );

        return UserJoinResponse.builder()
                .email(savedUser.getEmail())
                .build();
    }

    public UserLoginResponse login(UserLoginRequest dto) {
        User user = userJpaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                            AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PASSWORD, AppErrorCode.INVALID_PASSWORD.getMessage());
        }

        String generatedJwt = JwtTokenUtil.createToken(user.getEmail(), secretKey, expireTimeMs);

        if (redisAccessTokenUtil.hasAccessToken(user.getEmail())) {
            redisAccessTokenUtil.saveBlockAccessToken(
                    redisAccessTokenUtil.getAccessToken(dto.getEmail())
            );
        }
        redisAccessTokenUtil.saveAccessToken(user.getEmail(), generatedJwt);
        return UserLoginResponse.builder()
                .jwt(generatedJwt)
                .build();
    }

    public Boolean isNotEmailExist(String targetEmail) {
        Boolean isNotEmailExist = true;
        Optional<User> user = userJpaRepository.findByEmail(targetEmail);
        if (user.isPresent()) {
            isNotEmailExist = false;
        }

        return isNotEmailExist;
    }

    public User getUserByEmail(String email) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND, AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
        return user;
    }

    public UserLogoutResponse logout(Authentication authentication) {
        String logoutResultMsg = "";
        String email = authentication.getName();
        log.info(email);
        if (redisAccessTokenUtil.hasAccessToken(email)) {

            String jwt = redisAccessTokenUtil.getAccessToken(email);
            redisAccessTokenUtil.saveBlockAccessToken(jwt);
            redisAccessTokenUtil.deleteAccessToken(email);

            logoutResultMsg = email;
        }
        return UserLogoutResponse.builder()
                .logoutResult(logoutResultMsg)
                .build();
    }
}
