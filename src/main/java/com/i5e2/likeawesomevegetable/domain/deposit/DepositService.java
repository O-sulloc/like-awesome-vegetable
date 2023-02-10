package com.i5e2.likeawesomevegetable.domain.deposit;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingResponse;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.deposit.entity.UserPointDeposit;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;
import com.i5e2.likeawesomevegetable.domain.market.exception.PostErrorCode;
import com.i5e2.likeawesomevegetable.domain.market.exception.PostException;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.UserException;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPointDepositJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPointJpaRepository;
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
    public Result<DepositPendingResponse> addUserPendingDeposit(DepositPendingRequest depositPendingRequest, String userEmail) {
        UserPoint findUserPoint = userPointJpaRepository.findById(getUserOne(userEmail).getId())
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
        userPointJpaRepository.save(findUserPoint);

        PostPointActivateEnum updatePostActivate = updatePostActivate(depositPendingRequest.getDepositTargetPostId());
        return Result.success(DepositFactory.from(pendingDeposit, updatePostActivate));
    }

    private PostPointActivateEnum updatePostActivate(Long depositTargetPostId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(depositTargetPostId)
                .orElseThrow(() -> {
                    throw new PostException(PostErrorCode.POST_NOT_FOUND,
                            PostErrorCode.POST_NOT_FOUND.getMessage());
                });
        companyBuying.updatePostActivate(PostPointActivateEnum.ABLE);
        return companyBuyingJpaRepository.save(companyBuying).getPostPointActivate();
    }

    private User getUserOne(String userEmail) {
        return userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND,
                            UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }
}
