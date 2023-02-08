package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MarketDetailService {

    private final CompanyBuyingJpaRepository buyingJpaRepository;

    public CompanyBuyingDetailResponse getGatherDetail(Long buyingId) {

        CompanyBuying companyBuying = buyingJpaRepository.findById(buyingId)
                .orElseThrow(() -> new NoSuchElementException("없는 모집글"));

        CompanyBuyingDetailResponse detailResponse = CompanyBuyingDetailResponse.builder()
                .id(companyBuying.getId())
                .buyingTitle(companyBuying.getBuyingTitle())
                .buyingDescription(companyBuying.getBuyingDescription())
                .buyingStartTime(companyBuying.getBuyingStartTime())
                .buyingEndTime(companyBuying.getBuyingEndTime())
                .buyingItem(companyBuying.getBuyingItem())
                .buyingItemCategory(companyBuying.getBuyingItemCategory())
                .buyingPrice(companyBuying.getBuyingPrice())
                .buyingQuantity(companyBuying.getBuyingPrice())
                .buyingQuantity(companyBuying.getBuyingQuantity())
                .buyingShipping(companyBuying.getBuyingShipping())
                .receiverAddress(companyBuying.getReceiverAddress())
                .receiverName(companyBuying.getReceiverName())
                .receiverPhoneNo(companyBuying.getReceiverPhoneNo())
                .companyUserId(companyBuying.getCompanyUser().getId())
                .companyBuyingStatus(companyBuying.getParticipationStatus())
                .postPointActivate(companyBuying.getPostPointActivate())
                .buyingRegisteredAt(companyBuying.getBuyingRegisteredAt())
                .buyingModifiedAt(companyBuying.getBuyingModifiedAt())
                .buyingDeletedAt(companyBuying.getBuyingDeletedAt())
                .build();

        return detailResponse;
    }


}
