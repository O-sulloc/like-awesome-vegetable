package com.i5e2.likeawesomevegetable.domain.user;

import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserJpaRepository userJpaRepository;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expireTimeMs = 10000 * 60 * 60;

    public UserJoinResponse join(UserJoinRequest dto) {
        if (!isNotEmailExist(dto.getEmail())) {
            throw new UserException(UserErrorCode.DUPLICATED_EMAIL,
                    UserErrorCode.DUPLICATED_EMAIL.getMessage());
        }
        User savedUser = userJpaRepository.save(
                User.builder()
                        .email(dto.getEmail())
                        .managerName(dto.getManagerName())
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

        return UserLoginResponse.builder()
                .jwt(JwtTokenUtil.createToken(user.getEmail(), secretKey, expireTimeMs))
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
}
