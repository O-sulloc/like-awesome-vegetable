package com.i5e2.likeawesomevegetable.domain.admin;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.admin.dto.DepositTransferResponse;
import com.i5e2.likeawesomevegetable.domain.deposit.DepositFactory;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositStatus;
import com.i5e2.likeawesomevegetable.domain.deposit.entity.UserPointDeposit;
import com.i5e2.likeawesomevegetable.repository.UserPointDepositJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositApiService {
    private final UserPointDepositJpaRepository userPointDepositJpaRepository;

    public DepositTransferResponse updateDepositStatus(Long pointUserId) {
        UserPointDeposit userPointDeposit = userPointDepositJpaRepository.findByUserPointId(pointUserId)
                .orElseThrow(() -> new NotFoundException("해당 유저의 보증금 내역이 존재하지 않습니다."));
        userPointDeposit.updateDepositStatus(DepositStatus.TRANSFER);
        userPointDepositJpaRepository.save(userPointDeposit);
        return DepositFactory.createTransferResponse(userPointDeposit);
    }
}
