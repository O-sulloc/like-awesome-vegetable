package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.item.Item;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MarketDetailService {

    private final CompanyBuyingJpaRepository buyingJpaRepository;
    private final FarmAuctionJpaRepository auctionJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final ImgFindService imgFindService;

    public CompanyBuyingDetailResponse getGatherDetail(Long buyingId) {

        CompanyBuying companyBuying = buyingJpaRepository.findById(buyingId)
                .orElseThrow(() -> new NoSuchElementException("없는 모집글"));

        Item item = itemJpaRepository.findByItemCode(companyBuying.getBuyingItem());
        String itemName = item.getItemName();
        String itemCategoryName = item.getItemCategoryName();

        CompanyBuyingDetailResponse detailResponse = CompanyBuyingDetailResponse.builder()
                .id(companyBuying.getId())
                .buyingTitle(companyBuying.getBuyingTitle())
                .buyingDescription(companyBuying.getBuyingDescription())
                .buyingStartTime(companyBuying.getBuyingStartTime())
                .buyingEndTime(companyBuying.getBuyingEndTime())
                .buyingItem(itemName)
                .buyingItemCategory(itemCategoryName)
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

    public FarmAuctionDetailResponse getAuctionDetail(Long auctionId) {
        FarmAuction farmAuction = auctionJpaRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("없는 경매글"));

        Item item = itemJpaRepository.findByItemCode(farmAuction.getAuctionItem());
        String itemName = item.getItemName();
        String itemCategoryName = item.getItemCategoryName();

        List<ImgFindListResponse> imageList = imgFindService.findAuctionImg(auctionId);

        FarmAuctionDetailResponse detailResponse = FarmAuctionDetailResponse.builder()
                .id(farmAuction.getId())
                .auctionTitle(farmAuction.getAuctionTitle())
                .auctionDescription(farmAuction.getAuctionDescription())
                .auctionStartTime(farmAuction.getAuctionStartTime())
                .auctionEndTime(farmAuction.getAuctionEndTime())
                .auctionItem(itemName)
                .auctionItemCategory(itemCategoryName)
                .auctionStartPrice(farmAuction.getAuctionStartPrice())
                .auctionLimitPrice(farmAuction.getAuctionLimitPrice())
                .auctionHighestPrice(Math.toIntExact(farmAuction.getAuctionHighestPrice()))
                .auctionQuantity(farmAuction.getAuctionQuantity())
                .auctionShipping(farmAuction.getAuctionShipping())
                .farmUserId(farmAuction.getFarmUser().getId())
                .auctionStatus(farmAuction.getParticipationStatus())
                .postPointActivate(farmAuction.getPostPointActivate())
                .auctionRegisteredAt(farmAuction.getAuctionRegisteredAt())
                .auctionModifiedAt(farmAuction.getAuctionModifiedAt())
                .auctionDeletedAt(farmAuction.getAuctionDeletedAt())
                .images(imageList)
                .build();

        return detailResponse;
    }
}
