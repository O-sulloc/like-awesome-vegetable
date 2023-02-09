package com.i5e2.likeawesomevegetable.domain.user.inquiry;

import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyErrorCode;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.ParticipationStatus;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.CompanyDetailResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.CompanyListResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.CompanyUserResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.BuyingListResponse;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.file.CompanyFileUploadService;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyUserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyInquiryService {

    private final CompanyUserJpaRepository companyUserJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final CompanyFileUploadService companyFileUploadService;

    public Page<CompanyListResponse> list(Pageable pageable) {

        return companyUserJpaRepository.findAll(pageable)
                .map(companyUser -> CompanyListResponse.builder()
                        .id(companyUser.getId())
                        .companyName(companyUser.getCompanyName())
                        .companyMajorItem(itemJpaRepository.findByItemCode(companyUser.getCompanyMajorItem()).getItemName())
                        .companyAddress(companyUser.getCompanyAddress())
                        .build()
        );
    }

    public CompanyDetailResponse detail(Long companyId, Pageable pageable) {

        CompanyUser companyUser = companyUserJpaRepository.findById(companyId)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.USER_NOT_FOUND, ApplyErrorCode.USER_NOT_FOUND.getMessage()));

        // 기업 이미지
        String companyImage = companyFileUploadService.getCompanyImage(companyId.toString());

        // 기업 소개
        CompanyUserResponse companyUserResponse = CompanyUserResponse
                .fromEntity(companyUser, itemJpaRepository.findByItemCode(companyUser.getCompanyMajorItem()).getItemName());

        // 현재 진행중인 모집 목록
        Page<CompanyBuying> companyBuyings = companyBuyingJpaRepository
                .findAllByCompanyUserIdAndParticipationStatus(companyId, ParticipationStatus.UNDERWAY, pageable);

        Page<BuyingListResponse> buyingListResponses = companyBuyings.map(buyingListResponse -> BuyingListResponse
                .fromEntity(buyingListResponse,
                        itemJpaRepository.findByItemCode(buyingListResponse.getBuyingItem()).getItemCategoryName(),
                        itemJpaRepository.findByItemCode(buyingListResponse.getBuyingItem()).getItemName()));

        return CompanyDetailResponse.builder()
                .companyImage(companyImage)
                .companyUserResponse(companyUserResponse)
                .buyingListResponses(buyingListResponses)
                .build();
    }
}