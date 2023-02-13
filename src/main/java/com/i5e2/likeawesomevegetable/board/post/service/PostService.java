package com.i5e2.likeawesomevegetable.board.post.service;

import com.i5e2.likeawesomevegetable.company.buying.CompanyBuying;
import com.i5e2.likeawesomevegetable.company.buying.dto.BuyingListResponse;
import com.i5e2.likeawesomevegetable.company.buying.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.farm.auction.FarmAuction;
import com.i5e2.likeawesomevegetable.farm.auction.dto.AuctionListResponse;
import com.i5e2.likeawesomevegetable.farm.auction.repository.FarmAuctionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;

    @Transactional
    public Page<BuyingListResponse> getBuyingList(Pageable pageable) {
        List<CompanyBuying> buyings = companyBuyingJpaRepository.findAllByPostPointActivate(pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), buyings.size());
        Page<CompanyBuying> page = new PageImpl<>(buyings.subList(start, end), pageable, buyings.size());
        Page<BuyingListResponse> buyingLists = page.map(
                buying -> BuyingListResponse.builder()
                        .id(buying.getId())
                        .companyName(buying.getCompanyUser().getCompanyName())
                        .buyingTitle(buying.getBuyingTitle())
                        .buyingEndTime(buying.getBuyingEndTime())
                        .buyingItemCategory(buying.getBuyingItemCategory())
                        .buyingItem(buying.getBuyingItem())
                        .buyingQuantity(buying.getBuyingQuantity())
                        .buyingPrice(buying.getBuyingPrice())
                        .companyBuyingStatus(buying.getParticipationStatus())
                        .postPointActivate(buying.getPostPointActivate())
                        .build()
        );
        return buyingLists;
    }

    @Transactional
    public Page<AuctionListResponse> getAuctionList(Pageable pageable) {
        List<FarmAuction> auctions = farmAuctionJpaRepository.findAllByPostPointActivate(pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), auctions.size());
        Page<FarmAuction> page = new PageImpl<>(auctions.subList(start, end), pageable, auctions.size());
        Page<AuctionListResponse> auctionLists = page.map(
                auction -> AuctionListResponse.builder()
                        .id(auction.getId())
                        .farmOwnerName(auction.getFarmUser().getFarmOwnerName())
                        .auctionTitle(auction.getAuctionTitle())
                        .auctionStartTime(auction.getAuctionStartTime())
                        .auctionEndTime(auction.getAuctionEndTime())
                        .auctionItemCategory(auction.getAuctionItemCategory())
                        .auctionItem(auction.getAuctionItem())
                        .auctionQuantity(auction.getAuctionQuantity())
                        .auctionHighestPrice(auction.getAuctionHighestPrice())
                        .postPointActivate(auction.getPostPointActivate())
                        .farmAuctionStatus(auction.getParticipationStatus())
                        .build()
        );
        return auctionLists;
    }

    @Transactional
    public Page<BuyingListResponse> getHotBuytingList(Pageable pageable) {
        List<CompanyBuying> buyings = companyBuyingJpaRepository.findAllByPostPointActivatewithHot(pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), buyings.size());
        Page<CompanyBuying> page = new PageImpl<>(buyings.subList(start, end), pageable, buyings.size());
        Page<BuyingListResponse> buyingLists = page.map(
                buying -> BuyingListResponse.builder()
                        .id(buying.getId())
                        .companyName(buying.getCompanyUser().getCompanyName())
                        .buyingTitle(buying.getBuyingTitle())
                        .buyingEndTime(buying.getBuyingEndTime())
                        .buyingItemCategory(buying.getBuyingItemCategory())
                        .buyingItem(buying.getBuyingItem())
                        .buyingQuantity(buying.getBuyingQuantity())
                        .buyingPrice(buying.getBuyingPrice())
                        .companyBuyingStatus(buying.getParticipationStatus())
                        .postPointActivate(buying.getPostPointActivate())
                        .build()
        );
        return buyingLists;
    }

    @Transactional
    public Page<AuctionListResponse> getHotAuctionList(Pageable pageable) {
        List<FarmAuction> auctions = farmAuctionJpaRepository.findAllByPostPointActivatewithHot(pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), auctions.size());
        Page<FarmAuction> page = new PageImpl<>(auctions.subList(start, end), pageable, auctions.size());
        Page<AuctionListResponse> auctionLists = page.map(
                auction -> AuctionListResponse.builder()
                        .id(auction.getId())
                        .farmOwnerName(auction.getFarmUser().getFarmOwnerName())
                        .auctionTitle(auction.getAuctionTitle())
                        .auctionStartTime(auction.getAuctionStartTime())
                        .auctionEndTime(auction.getAuctionEndTime())
                        .auctionItemCategory(auction.getAuctionItemCategory())
                        .auctionItem(auction.getAuctionItem())
                        .auctionQuantity(auction.getAuctionQuantity())
                        .auctionHighestPrice(auction.getAuctionHighestPrice())
                        .postPointActivate(auction.getPostPointActivate())
                        .farmAuctionStatus(auction.getParticipationStatus())
                        .build()
        );
        return auctionLists;
    }
}
