package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyErrorCode;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final CompanyBuyingJpaRepository buyingJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;

    public String creatBuyingNoneAuth(BuyingRequest buyingRequest) {

        CompanyBuying companyBuying = buyingRequest.toEntityNoneAuth(buyingRequest);
        buyingJpaRepository.save(companyBuying);

        return null;
    }

    public BuyingResponse creatBuying(BuyingRequest buyingRequest, String email) {
        User user = userJpaRepository.findByEmail(email).get();
        CompanyUser companyUser = user.getCompanyUser();

        CompanyBuying companyBuying = buyingRequest.toEntity(buyingRequest, companyUser);
        buyingJpaRepository.save(companyBuying);

        BuyingResponse buyingResponse = BuyingResponse.builder()
                .buyingId(companyBuying.getId())
                .message("모집 게시글 작성 완료")
                .build();
        return buyingResponse;
    }

    // 모집 종료
    public void applyEnd(Long companyBuyingId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.POST_NOT_FOUND, ApplyErrorCode.POST_NOT_FOUND.getMessage()));

        applyJpaRepository.findAllByCompanyBuyingId(companyBuyingId).forEach(Apply::updateStatusToEnd);

        companyBuying.updateStatusToEnd();
    }
}
