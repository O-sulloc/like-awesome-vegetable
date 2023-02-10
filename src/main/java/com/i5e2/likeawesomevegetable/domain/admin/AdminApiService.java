package com.i5e2.likeawesomevegetable.domain.admin;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminJoinRequest;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminLoginResponse;
import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import com.i5e2.likeawesomevegetable.domain.user.UserLoginRequest;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.AdminUserJpaRepository;
import com.i5e2.likeawesomevegetable.security.JwtTokenUtil;
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

    public Result join(AdminJoinRequest adminJoinRequest) {
        AdminUser adminUser = AdminFactory
                .createAdminUser(adminJoinRequest, encoder.encode(adminJoinRequest.getAdminPassword()));
        AdminUser saveAdmin = adminUserJpaRepository.save(adminUser);
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
