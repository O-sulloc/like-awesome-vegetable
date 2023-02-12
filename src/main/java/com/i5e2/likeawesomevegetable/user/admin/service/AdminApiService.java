package com.i5e2.likeawesomevegetable.user.admin.service;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.security.JwtTokenUtil;
import com.i5e2.likeawesomevegetable.user.admin.AdminFactory;
import com.i5e2.likeawesomevegetable.user.admin.AdminUser;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminJoinRequest;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminLoginResponse;
import com.i5e2.likeawesomevegetable.user.admin.repository.AdminUserJpaRepository;
import com.i5e2.likeawesomevegetable.user.basic.dto.UserLoginRequest;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminApiService {

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    private long expireTimeMs = 1000 * 60 * 60; // 1h

    private final AdminUserJpaRepository adminUserJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Result join(AdminJoinRequest adminJoinRequest) {
        AdminUser adminUser = AdminFactory
                .createAdminUser(adminJoinRequest, encoder.encode(adminJoinRequest.getAdminPassword()));
        AdminUser saveAdmin = adminUserJpaRepository.save(adminUser);
        userJpaRepository.save(AdminFactory.createUser(adminUser));
        return Result.success(AdminFactory.from(saveAdmin));
    }

    public Result<AdminLoginResponse> login(UserLoginRequest adminLoginRequest) {
        AdminUser adminUser = adminUserJpaRepository.findByAdminEmail(adminLoginRequest.getEmail())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                            AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });

        if (!encoder.matches(adminLoginRequest.getPassword(), adminUser.getAdminPassword())) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PASSWORD, AppErrorCode.INVALID_PASSWORD.getMessage());
        }

        String jwt = JwtTokenUtil.createToken(adminUser.getAdminEmail(), secretKey, expireTimeMs);

        return Result.success(AdminFactory.of(adminUser.getId(), adminUser.getAdminEmail(), jwt));
    }
}
