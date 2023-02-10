package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypagePointEvenLogResponse;
import com.i5e2.likeawesomevegetable.domain.point.PointFactory;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.verification.UserVerification;
import com.i5e2.likeawesomevegetable.domain.verification.Verification;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserVerificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageApiService {
    private final UserJpaRepository userJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;
    private final UserVerificationJpaRepository userVerificationJpaRepository;

    @Transactional(readOnly = true)
    public List<MypagePointEvenLogResponse> readUserPointLogs(String userEmail) {
        Long userId = getUser(userEmail).getId();
        try {
            return pointEventLogJpaRepository.getPointUserId(userId).stream()
                    .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new AwesomeVegeAppException(AppErrorCode.EMPTY_POINT_RESULT,
                    AppErrorCode.EMPTY_POINT_RESULT.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<MypagePointEvenLogResponse> readAdminPointLogs(String userEmail) {
        Long userId = getUser(userEmail).getId();
        return pointEventLogJpaRepository.getPointAdminId(userId).stream()
                .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                .collect(Collectors.toList());
    }

    private User getUser(String userEmail) {
        return userJpaRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                    AppErrorCode.EMAIL_NOT_FOUND.getMessage());
        });
    }

    @Transactional
    public Result<String> makeUserVerification(String loginEmail) {
        User loginUser = userJpaRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.LOGIN_USER_NOT_FOUND,
                        AppErrorCode.LOGIN_USER_NOT_FOUND.getMessage()
                ));
        
        if (userVerificationJpaRepository.findByUserId(loginUser.getId()).isPresent()) {
            userVerificationJpaRepository.delete(userVerificationJpaRepository.findByUserId(loginUser.getId()).get());
        }

        UserVerification loginUserVerification = UserVerification.builder()
                .loginUser(loginUser)
                .verificationEmail(Verification.NOT_VERIFIED)
                .verificationUrl(Verification.NOT_VERIFIED)
                .verificationBusiness(Verification.NOT_VERIFIED)
                .build();

        userVerificationJpaRepository.save(loginUserVerification);

        return Result.success(loginEmail + "검증 entity 생성");
    }

}
