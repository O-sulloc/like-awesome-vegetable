package com.i5e2.likeawesomevegetable.domain.point;

import com.i5e2.likeawesomevegetable.domain.deposit.entity.UserPointDeposit;
import com.i5e2.likeawesomevegetable.domain.deposit.exception.DepositErrorCode;
import com.i5e2.likeawesomevegetable.domain.deposit.exception.DepositException;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentOrderPointResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentRefundResponse;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.point.exception.PointErrorCode;
import com.i5e2.likeawesomevegetable.domain.point.exception.PointException;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.UserException;
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

    public UserPointResponse checkUserPointInfo(String userMail) {
        User getUser = getUser(userMail);
        Optional<UserPoint> userPoint = userPointJpaRepository.findByUser(getUser);

        if (userPoint.isPresent()) {
            UserPoint updateUserPoint = updateUserTotalPoint(getUser.getId(), userPoint.get());
            return PointFactory.from(updateUserPoint);
        } else {
            getTotalPointBalanceByUser(getUser.getId());
            UserPoint addUserPoint = addUserPointInfo(getUser);
            return PointFactory.from(addUserPoint);
        }
    }

    public UserPointResponse updateUserPointInfo(String userEmail) {
        User getUser = getUser(userEmail);
        UserPoint userPoint = userPointJpaRepository.findByUser(getUser)
                .orElseThrow(() -> {
                    throw new PointException(PointErrorCode.NO_POINT_RESULT,
                            PointErrorCode.NO_POINT_RESULT.getMessage());
                });

        UserPointDeposit userPointDeposit = userPointDepositJpaRepository.findByUserPointId(getUser.getId())
                .orElseThrow(() -> {
                    throw new DepositException(DepositErrorCode.NO_POINT_DEPOSIT_RESULT,
                            DepositErrorCode.NO_POINT_DEPOSIT_RESULT.getMessage());
                });

        userPoint.updateDepositTotalBalance(userPoint.getDepositTotalBalance() - userPointDeposit.getDepositAmount());
        userPoint.updatePointTotalBalance(userPoint.getPointTotalBalance() - userPointDeposit.getDepositAmount());
        UserPoint updateDepositResult = userPointJpaRepository.save(userPoint);
        return PointFactory.from(updateDepositResult);
    }

    public UserPointResponse refundPoint(PaymentRefundResponse paymentRefundResponse, String cancelUserEmail) {
        UserPoint cancelUserPoint = userPointJpaRepository.findByUser(getUser(cancelUserEmail))
                .orElseThrow(() -> {
                    throw new PointException(PointErrorCode.NO_POINT_RESULT,
                            PointErrorCode.NO_POINT_RESULT.getMessage());
                });

        cancelUserPoint.updatePointTotalBalance(cancelUserPoint.getPointTotalBalance() - paymentRefundResponse.getTotalAmount());
        userPointJpaRepository.save(cancelUserPoint);
        return PointFactory.from(cancelUserPoint);
    }

    @Transactional(readOnly = true)
    public PointTotalBalanceDto getTotalPointBalanceByUser(Long userId) {
        try {
            return pointEventLogJpaRepository.getUserTotalBalance(userId);
        } catch (NullPointerException e) {
            throw new PointException(PointErrorCode.EMPTY_POINT_RESULT,
                    PointErrorCode.EMPTY_POINT_RESULT.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public PaymentOrderPointResponse comparePointDeposit(PaymentInfoRequest paymentInfoRequest, String userEmail) {
        User findUser = getUser(userEmail);
        UserPoint userPointDeposit = userPointJpaRepository.findByUser(findUser)
                .orElseThrow(() -> {
                    throw new PointException(PointErrorCode.NO_POINT_RESULT
                            , PointErrorCode.NO_POINT_RESULT.getMessage());
                });
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

    private User getUser(String userMail) {
        return userJpaRepository.findByEmail(userMail)
                .orElseThrow(() -> {
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND
                            , UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }
}
