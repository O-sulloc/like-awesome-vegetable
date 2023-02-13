package com.i5e2.likeawesomevegetable.payment.deposit.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.payment.deposit.DepositFactory;
import com.i5e2.likeawesomevegetable.payment.deposit.UserPointDeposit;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositStatus;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositTransferResponse;
import com.i5e2.likeawesomevegetable.payment.deposit.repository.UserPointDepositJpaRepository;
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
