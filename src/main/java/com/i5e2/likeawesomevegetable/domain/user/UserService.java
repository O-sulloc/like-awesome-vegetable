package com.i5e2.likeawesomevegetable.domain.user;

import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.security.JwtTokenUtil;
import com.i5e2.likeawesomevegetable.security.RedisAccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
            throw new UserException(UserErrorCode.DUPLICATED_EMAIL,
                    UserErrorCode.DUPLICATED_EMAIL.getMessage());
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
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND,
                            UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD, UserErrorCode.INVALID_PASSWORD.getMessage());
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
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND, UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
        return user;
    }

    public UserLogoutResponse logout(Authentication authentication) {
        String logoutResultMsg = "";
        String email = authentication.getName();
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
