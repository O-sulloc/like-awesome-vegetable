package com.i5e2.likeawesomevegetable.domain.deposit;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositPendingResponse;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositTotalBalanceDto;
import com.i5e2.likeawesomevegetable.domain.deposit.entity.UserPointDeposit;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.PostPointActivateEnum;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
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
    private final UserPointDepositJpaRepository userPointDepositJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;

    @Transactional(rollbackFor = Exception.class)
    public Result<DepositPendingResponse> addUserPendingDeposit(DepositPendingRequest depositPendingRequest) {
        UserPoint findUserPoint = userPointJpaRepository.findById(depositPendingRequest.getUserPointId())
                .orElseThrow(() -> new NotFoundException("사용자 포인트 정보가 존재하지 않습니다"));

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
                .orElseThrow(() -> new NotFoundException("해당 모집글이 존재하지 않습니다."));
        companyBuying.updatePostActivate(PostPointActivateEnum.ABLE);
        return companyBuyingJpaRepository.save(companyBuying).getPostPointActivate();
    }
}
