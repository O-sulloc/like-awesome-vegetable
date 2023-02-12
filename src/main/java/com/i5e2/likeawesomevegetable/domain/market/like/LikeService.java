package com.i5e2.likeawesomevegetable.domain.market.like;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.alarm.Alarm;
import com.i5e2.likeawesomevegetable.domain.alarm.AlarmDetail;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingLike;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuction;
import com.i5e2.likeawesomevegetable.domain.market.FarmAuctionLike;
import com.i5e2.likeawesomevegetable.domain.market.like.dto.LikeResponse;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final UserJpaRepository userJpaRepository;
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final FarmAuctionLikeJpaRepository farmAuctionLikeJpaRepository;
    private final CompanyBuyingLikeJpaRepository companyBuyingLikeJpaRepository;

    private final AlarmJpaRepository alarmJpaRepository;

    /*     농가 경매 게시글 좋아요     */
    public Result<LikeResponse> auctionPostLike(String loginEmail, Long auctionPostId) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 경매 게시글 확인
        FarmAuction selectAuctionPost = validateFarmAuction(auctionPostId);

        // 기업 유저인 경우만 좋아요 누를 수 있음
        if (loginUser.getCompanyUser() == null) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, "기업 사용자만 좋아요가 가능합니다.");
        }

        // 좋아요 있는 경우 좋아요 튜플 Hard Delete
        if (farmAuctionLikeJpaRepository.findByUserAndFarmAuction(loginUser, selectAuctionPost).isPresent()) {
            LikeResponse response = LikeResponse.of(farmAuctionLikeJpaRepository.findByUserAndFarmAuction(
                    loginUser, selectAuctionPost).get(), "좋아요를 취소했습니다.");
            farmAuctionLikeJpaRepository.delete(farmAuctionLikeJpaRepository.findByUserAndFarmAuction(loginUser, selectAuctionPost).get());
            return Result.success(response);
        }

        FarmAuctionLike farmAuctionLike = FarmAuctionLike.builder()
                .farmAuction(selectAuctionPost)
                .loginUser(loginUser)
                .build();
        farmAuctionLikeJpaRepository.save(farmAuctionLike);

        LikeResponse likeResponse = LikeResponse.of(farmAuctionLike, "좋아요를 눌렀습니다.");
        return Result.success(likeResponse);
    }

    public Result<LikeResponse> buyingPostLike(String loginEmail, Long buyingPostId) {
        // 로그인 유저 확인
        User loginUser = validateLoginUser(loginEmail);

        // 기업 구매 게시글 확인
        CompanyBuying selectBuyingPost = validateCompanyBuying(buyingPostId);

        // 농가 유저인 경우만 좋아요 누를 수 있음
        if (loginUser.getFarmUser() == null) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, "농가 사용자만 좋아요가 가능합니다.");
        }

        // 좋아요 있는 경우 좋아요 튜플 Hard Delete
        if (companyBuyingLikeJpaRepository.findByUserAndCompanyBuying(loginUser, selectBuyingPost).isPresent()) {
            LikeResponse likeResponse = LikeResponse.of(companyBuyingLikeJpaRepository.findByUserAndCompanyBuying(
                    loginUser, selectBuyingPost).get(), "좋아요를 취소했습니다.");
            companyBuyingLikeJpaRepository.delete(companyBuyingLikeJpaRepository.findByUserAndCompanyBuying(loginUser, selectBuyingPost).get());
            return Result.success(likeResponse);
        }

        CompanyBuyingLike companyBuyingLike = CompanyBuyingLike.builder()
                .companyBuying(selectBuyingPost)
                .loginUser(loginUser)
                .build();
        companyBuyingLikeJpaRepository.save(companyBuyingLike);

        LikeResponse likeResponse = LikeResponse.of(companyBuyingLike, "좋아요를 눌렀습니다.");
        // TODO - alarm
        Alarm alarm = Alarm.builder()
                .alarmDetail(AlarmDetail.LIKE)
                .alarmTriggerId(selectBuyingPost.getId())
                .alarmRead(Boolean.FALSE)
                .alarmSenderId(loginUser.getId())
                .user(userJpaRepository.findByCompanyUserId(selectBuyingPost.getCompanyUser().getId()).get())
                .build();
        alarmJpaRepository.save(alarm);
        return Result.success(likeResponse);
    }

    public User validateLoginUser(String loginEmail) {
        User loginUser = userJpaRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.EMAIL_NOT_FOUND,
                        AppErrorCode.EMAIL_NOT_FOUND.getMessage())
                );
        return loginUser;
    }

    public FarmAuction validateFarmAuction(Long auctionPostId) {
        FarmAuction farmAuction = farmAuctionJpaRepository.findById(auctionPostId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );
        return farmAuction;
    }

    public CompanyBuying validateCompanyBuying(Long buyingPostId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(buyingPostId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.POST_NOT_FOUND,
                        AppErrorCode.POST_NOT_FOUND.getMessage())
                );
        return companyBuying;
    }

}
