package com.i5e2.likeawesomevegetable.domain.user.inquiry;

import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.market.ParticipationStatus;
import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.AuctionListResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.FarmDetailResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.FarmListResponse;
import com.i5e2.likeawesomevegetable.domain.user.inquiry.dto.FarmUserResponse;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmImageJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
import com.i5e2.likeawesomevegetable.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmInquiryService {

    private final FarmUserRepository farmUserRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final FarmImageJpaRepository farmImageJpaRepository;

    public Page<FarmListResponse> list(Pageable pageable) {

        return farmUserRepository.findAll(pageable)
                .map(farmUser -> FarmListResponse.builder()
                        .id(farmUser.getId())
                        .farmOwnerName(farmUser.getFarmOwnerName())
                        .farmMajorItem(itemJpaRepository.findByItemCode(farmUser.getFarmMajorItem()).getItemName())
                        .farmAddress(farmUser.getFarmAddress())
                        .build()
                );
    }

    public FarmDetailResponse detail(Long farmId, Pageable pageable) {

        FarmUser farmUser = farmUserRepository.findById(farmId)
                .orElseThrow(() -> new AwesomeVegeAppException(AppErrorCode.USER_NOT_FOUND, AppErrorCode.USER_NOT_FOUND.getMessage()));

        // 농가 이미지 가져오기
        List<FarmImageLink> farmImage = farmImageJpaRepository.findAllByFarmUserId(farmId);

        // 농가 소개
        FarmUserResponse farmUserResponse = FarmUserResponse
                .fromEntity(farmUser, itemJpaRepository.findByItemCode(farmUser.getFarmMajorItem()).getItemName());

        // 현재 진행중인 경매 목록
        Page<FarmAuction> farmAuctions = farmAuctionJpaRepository
                .findAllByFarmUserIdAndParticipationStatus(farmId, ParticipationStatus.UNDERWAY, pageable);

        Page<AuctionListResponse> auctionListResponses = farmAuctions.map(auctionListResponse -> AuctionListResponse
                .fromEntity(auctionListResponse,
                        itemJpaRepository.findByItemCode(auctionListResponse.getAuctionItem()).getItemCategoryName(),
                        itemJpaRepository.findByItemCode(auctionListResponse.getAuctionItem()).getItemName()));

        return FarmDetailResponse.builder()
                .farmImage(farmImage)
                .farmUserResponse(farmUserResponse)
                .auctionListResponses(auctionListResponses)
                .build();
    }
}
