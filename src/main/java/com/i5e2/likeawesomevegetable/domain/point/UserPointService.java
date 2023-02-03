package com.i5e2.likeawesomevegetable.domain.point;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPointService {
    private final UserPointJpaRepository userPointJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    public UserPointResponse checkUserPointInfo(Long userId) {
        User getUser = getUser(userId);
        Optional<UserPoint> userPoint = userPointJpaRepository.findByUser(getUser);

        if (userPoint.isPresent()) {
            UserPoint updateUserPoint = updateUserTotalPoint(userId, userPoint.get());
            return PointFactory.from(updateUserPoint);
        } else {
            PointTotalBalanceDto totalPointBalanceByUser = getTotalPointBalanceByUser(userId);
            UserPoint addUserPoint = addUserPointInfo(getUser, totalPointBalanceByUser.getUserTotalBalance());
            return PointFactory.from(addUserPoint);
        }
    }

    public UserPoint addUserPointInfo(User user, Long userTotalPoint) {
        return userPointJpaRepository.save(PointFactory.of(user, userTotalPoint));
    }

    public UserPoint updateUserTotalPoint(Long userId, UserPoint userPoint) {
        PointTotalBalanceDto userPointInfo = getTotalPointBalanceByUser(userId);
        userPoint.updatePointTotalBalance(userPointInfo.getUserTotalBalance());
        return userPointJpaRepository.save(userPoint);
    }

    private User getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    private PointTotalBalanceDto getTotalPointBalanceByUser(Long userId) {
        return pointEventLogJpaRepository.getUserTotalBalance(userId);
    }
}
