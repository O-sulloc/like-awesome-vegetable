package com.i5e2.likeawesomevegetable.domain.point;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.point.dto.DepositAvailableStatus;
import com.i5e2.likeawesomevegetable.domain.point.dto.DepositTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPointDepositJpaRepository;
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
    private final UserPointDepositJpaRepository userPointDepositJpaRepository;

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

    public DepositTotalBalanceDto getTotalDepositBalanceByUser(Long userId) {
        return userPointDepositJpaRepository.getDepositTotalBalance(userId);
    }

    public Result<DepositAvailableStatus> comparePointDeposit(PaymentInfoRequest paymentInfoRequest) {
        //TODO: view 전체 데이터 응답으로 변경
        User findUser = getUser(paymentInfoRequest.getUserId());
        UserPoint userPointDeposit = userPointJpaRepository.findByUser(findUser)
                .orElseThrow(() -> new NotFoundException("사용자 포인트 정보가 존재하지 않습니다."));

        return (userPointDeposit.getPointTotalBalance() >= paymentInfoRequest.getRequestDepositAmount())
                ? Result.success(DepositAvailableStatus.DEPOSIT_AVAILABLE)
                : Result.success(DepositAvailableStatus.DEPOSIT_NOT_AVAILABLE);
    }

    private User getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    private PointTotalBalanceDto getTotalPointBalanceByUser(Long userId) {
        return pointEventLogJpaRepository.getUserTotalBalance(userId);
    }
}
