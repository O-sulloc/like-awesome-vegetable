package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypagePointEvenLogResponse;
import com.i5e2.likeawesomevegetable.domain.point.PointFactory;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.UserException;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageApiService {
    private final UserJpaRepository userJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    public List<MypagePointEvenLogResponse> readUserPointLogs(String userEmail) {
        Long userId = getUser(userEmail).getId();
        return pointEventLogJpaRepository.getPointUserId(userId).stream()
                .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                .collect(Collectors.toList());
    }

    public List<MypagePointEvenLogResponse> readAdminPointLogs(String userEmail) {
        Long userId = getUser(userEmail).getId();
        return pointEventLogJpaRepository.getPointAdminId(userId).stream()
                .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                .collect(Collectors.toList());
    }

    private User getUser(String userEmail) {
        return userJpaRepository.findByEmail(userEmail).orElseThrow(() -> {
            throw new UserException(UserErrorCode.EMAIL_NOT_FOUND,
                    UserErrorCode.EMAIL_NOT_FOUND.getMessage());
        });
    }
}
