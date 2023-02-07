package com.i5e2.likeawesomevegetable.domain.point;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.deposit.entity.UserPointDeposit;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentOrderPointResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentRefundResponse;
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

    private final UserPointDepositJpaRepository userPointDepositJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    public UserPointResponse checkUserPointInfo(Long userId) {
        User getUser = getUser(userId);
        Optional<UserPoint> userPoint = userPointJpaRepository.findByUser(getUser);

        if (userPoint.isPresent()) {
            UserPoint updateUserPoint = updateUserTotalPoint(userId, userPoint.get());
            return PointFactory.from(updateUserPoint);
        } else {
            getTotalPointBalanceByUser(userId);
            UserPoint addUserPoint = addUserPointInfo(getUser);
            return PointFactory.from(addUserPoint);
        }
    }

    public UserPointResponse updateUserPointInfo(Long userId) {
        User getUser = getUser(userId);
        UserPoint userPoint = userPointJpaRepository.findByUser(getUser)
                .orElseThrow(() -> new NotFoundException("사용자 포인트 정보가 존재하지 않습니다."));

        UserPointDeposit userPointDeposit = userPointDepositJpaRepository.findByUserPointId(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저의 보증금 정보가 존재하지 않습니다"));

        userPoint.updateDepositTotalBalance(userPoint.getDepositTotalBalance() - userPointDeposit.getDepositAmount());
        userPoint.updatePointTotalBalance(userPoint.getPointTotalBalance() - userPointDeposit.getDepositAmount());
        UserPoint updateDepositResult = userPointJpaRepository.save(userPoint);
        return PointFactory.from(updateDepositResult);
    }

    public UserPointResponse refundPoint(PaymentRefundResponse paymentRefundResponse, Long cancelUserId) {
        UserPoint cancelUserPoint = userPointJpaRepository.findByUser(getUser(cancelUserId))
                .orElseThrow(() -> new NotFoundException("해당 유저의 포인트 정보가 존재하지 않습니다"));
        cancelUserPoint.updatePointTotalBalance(cancelUserPoint.getPointTotalBalance() - paymentRefundResponse.getTotalAmount());
        userPointJpaRepository.save(cancelUserPoint);
        return PointFactory.from(cancelUserPoint);
    }

    public PointTotalBalanceDto getTotalPointBalanceByUser(Long userId) {
        return pointEventLogJpaRepository.getUserTotalBalance(userId);
    }

    public PaymentOrderPointResponse comparePointDeposit(PaymentInfoRequest paymentInfoRequest) {
        User findUser = getUser(paymentInfoRequest.getUserId());
        UserPoint userPointDeposit = userPointJpaRepository.findByUser(findUser)
                .orElseThrow(() -> new NotFoundException("사용자 포인트 정보가 존재하지 않습니다."));
        return PointFactory.of(paymentInfoRequest, userPointDeposit);
    }

    private UserPoint addUserPointInfo(User user) {
        return userPointJpaRepository.save(PointFactory.of(user));
    }

    private UserPoint updateUserTotalPoint(Long userId, UserPoint userPoint) {
        PointTotalBalanceDto userPointInfo = getTotalPointBalanceByUser(userId);
        userPoint.updatePointTotalBalance(userPointInfo.getUserTotalBalance());
        return userPointJpaRepository.save(userPoint);
    }

    private User getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }
}
