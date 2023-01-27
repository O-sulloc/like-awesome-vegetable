package com.i5e2.likeawesomevegetable.domain.apply;

import com.i5e2.likeawesomevegetable.domain.apply.dto.ApplyRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.ApplyResponse;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ErrorCode;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FarmUserRepository farmUserRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;

    // 모집 참여 조회
    public Page<ApplyResponse> list(Long companyBuyingId, Pageable pageable) {

        return applyJpaRepository.findAllByCompanyBuyingId(companyBuyingId, pageable).map(ApplyResponse::fromEntity);
    }

    // 모집 진행률
    public double progress(Long companyBuyingId) {

        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new ApplyException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        return (double)applyJpaRepository.currentQuantity(companyBuyingId) / companyBuying.getQuantity() * 100;
    }

    // 모집 참여 신청하기
    public ApplyResponse apply(ApplyRequest request, Long companyBuyingId, String userEmail) {

        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new ApplyException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApplyException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        // 신청자가 농가 사용자인지 확인
        farmUserRepository.findById(user.getFarmUser().getId())
                .orElseThrow(() -> new ApplyException(ErrorCode.NOT_FARM_USER, ErrorCode.NOT_FARM_USER.getMessage()));

        Apply savedApply = applyJpaRepository
                .save(request.toEntity(request.getSupplyQuantity(), companyBuying, user));

        // 모집 수량이 넘었거나 모집 시간이 종료되면 모집 완료
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        if (companyBuying.getQuantity() <= applyJpaRepository.currentQuantity(companyBuyingId)
                || LocalDateTime.now().isAfter(LocalDateTime.parse(companyBuying.getEndTime(), formatter))) {

            complete(companyBuyingId, userEmail);
        }

        return ApplyResponse.fromEntity(savedApply);
    }

    // 모집 완료
    public void complete(Long companyBuyingId, String userEmail) {

        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new ApplyException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        User user = userJpaRepository.findByEmail(userEmail)
                .filter(users -> Objects.equals(users.getEmail(), userEmail))
                .orElseThrow(() -> new ApplyException(ErrorCode.INVALID_PERMISSION,
                        ErrorCode.INVALID_PERMISSION.getMessage()));

        //TODO: 포인트 배분
    }
}