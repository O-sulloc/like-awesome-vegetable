package com.i5e2.likeawesomevegetable.domain.apply;

import com.i5e2.likeawesomevegetable.domain.apply.dto.BiddingRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.BiddingResponse;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyErrorCode;
import com.i5e2.likeawesomevegetable.domain.apply.exception.ApplyException;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.market.Standby;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.StandByJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BiddingService {

    private final StandByJpaRepository standByJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final String SMS_USER_ID = "SMS_USER_ID";

    // 경매 참여 조회
    public Page<BiddingResponse> list(Long farmAuctionId, Pageable pageable) {

        return standByJpaRepository.findAllByFarmAuctionId(farmAuctionId, pageable).map(BiddingResponse::fromEntity);
    }

    // 입찰 신청하기
    public BiddingResponse bidding(BiddingRequest request, Long farmAuctionId, String userEmail, HttpSession session) {

        // 경매 게시글이 있는지 확인
        FarmAuction farmAuction = farmAuctionJpaRepository.findById(farmAuctionId)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.POST_NOT_FOUND, ApplyErrorCode.POST_NOT_FOUND.getMessage()));

        // 로그인한 사용자인지 확인
        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.INVALID_PERMISSION, ApplyErrorCode.INVALID_PERMISSION.getMessage()));

        // 신청자가 기업 사용자인지 확인
        Optional<CompanyUser> companyUser = Optional.ofNullable(user.getCompanyUser());

        if (companyUser.isEmpty()) {
            throw new ApplyException(ApplyErrorCode.NOT_FARM_USER, ApplyErrorCode.NOT_FARM_USER.getMessage());
        }

        // 세션 확인
        Optional.ofNullable(session.getAttribute(SMS_USER_ID))
                .orElseThrow(() -> new ApplyException(ApplyErrorCode.INVALID_PERMISSION, ApplyErrorCode.INVALID_PERMISSION.getMessage()));

        this.biddingUpdate(farmAuctionId, request);

        Standby savedBidding = standByJpaRepository
                .save(request.toEntity(request.getBiddingPrice(), farmAuction, user, BiddingResult.SUCCESS));

        // 참여 고유번호 생성(BIDDING-날짜-게시글번호-대기ID)
        String biddingNumber = "BIDDING-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + "-" +
                farmAuctionId + "-" + savedBidding.getId();

        savedBidding.setBiddingNumber(biddingNumber);

        session.removeAttribute(SMS_USER_ID);
        return BiddingResponse.fromEntity(savedBidding);
    }

    // 입찰 상태 및 경매 최고가 업데이트
    @Transactional
    public void biddingUpdate(Long auctionId, BiddingRequest request) {

        // 경매글 highest price 낙찰가로 넣어주기
        FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("없는 경매글"));

        farmAuction.updateHighestPrice(request.getBiddingPrice());

        farmAuctionJpaRepository.save(farmAuction);

        if (standByJpaRepository.existsByFarmAuctionId(auctionId)) {
            // true(exist)면 경쟁 입찰자 있다는 의미

            // 이전 낙찰자 -> 패찰자 변경
            Standby pre = standByJpaRepository.findByFarmAuctionId(auctionId);
            log.info("pre:{}", pre.getBiddingPrice());

            pre.updateBiddingResult(BiddingResult.SLABLENESS);

            standByJpaRepository.save(pre);
        }
    }
}
