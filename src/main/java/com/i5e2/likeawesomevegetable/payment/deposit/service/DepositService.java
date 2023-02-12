package com.i5e2.likeawesomevegetable.payment.deposit.service;

import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;
import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.company.buying.CompanyBuying;
import com.i5e2.likeawesomevegetable.company.buying.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.payment.deposit.DepositFactory;
import com.i5e2.likeawesomevegetable.payment.deposit.UserPointDeposit;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositPendingResponse;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositTotalBalanceDto;
import com.i5e2.likeawesomevegetable.payment.deposit.repository.UserPointDepositJpaRepository;
import com.i5e2.likeawesomevegetable.payment.point.UserPoint;
import com.i5e2.likeawesomevegetable.payment.point.repository.UserPointJpaRepository;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {
    private final UserPointJpaRepository userPointJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserPointDepositJpaRepository userPointDepositJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;

    @Transactional(rollbackFor = Exception.class)
    public Result<DepositPendingResponse> addUserPendingDeposit(DepositPendingRequest depositPendingRequest) {
        UserPoint findUserPoint = userPointJpaRepository.findById(depositPendingRequest.getUserPointId())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.NO_POINT_RESULT,
                            AppErrorCode.NO_POINT_RESULT.getMessage());
                });

        if (findUserPoint.getPointTotalBalance() < depositPendingRequest.getDepositAmount()) {
            throw new AwesomeVegeAppException(AppErrorCode.DIPOSIT_AMOUNT_ERROR,
                    AppErrorCode.DIPOSIT_AMOUNT_ERROR.getMessage());
        }

        UserPointDeposit pendingDeposit = DepositFactory.createPendingDeposit(depositPendingRequest, findUserPoint);
        userPointDepositJpaRepository.save(pendingDeposit);
        //userPoint total deposit update
        DepositTotalBalanceDto depositTotalBalance = userPointDepositJpaRepository.getDepositTotalBalance(findUserPoint.getUser().getId());
        findUserPoint.updateDepositTotalBalance(depositTotalBalance.getDepositTotalAmount());

        if (findUserPoint.getDepositTotalBalance() > findUserPoint.getPointTotalBalance()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_REQUEST_DEPOSIT,
                    AppErrorCode.INVALID_REQUEST_DEPOSIT.getMessage());
        }
        userPointJpaRepository.save(findUserPoint);

        PostPointActivateEnum updatePostActivate = updatePostActivate(depositPendingRequest.getDepositTargetPostId());
        return Result.success(DepositFactory.from(pendingDeposit, updatePostActivate));
    }

    private PostPointActivateEnum updatePostActivate(Long depositTargetPostId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(depositTargetPostId)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.POST_NOT_FOUND,
                            AppErrorCode.POST_NOT_FOUND.getMessage());
                });
        companyBuying.updatePostActivate(PostPointActivateEnum.ABLE);
        return companyBuyingJpaRepository.save(companyBuying).getPostPointActivate();
    }

    private User getUserOne(String userEmail) {
        return userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                            AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }
}
