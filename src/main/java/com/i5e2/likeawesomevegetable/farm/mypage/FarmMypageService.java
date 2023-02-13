package com.i5e2.likeawesomevegetable.farm.mypage;

import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.company.apply.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.farm.auction.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.farm.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.farm.mypage.dto.FarmAuctionByUser;
import com.i5e2.likeawesomevegetable.mypage.MypageFactory;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmMypageService {
    private final FarmAuctionJpaRepository farmAuctionJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<FarmAuctionByUser> readFarmActionByUser(Pageable pageable, String userEmail) {
        Long farmUserId = getUser(userEmail).getFarmUser().getId();
        List<FarmAuctionByUser> farmAuctionByUser = farmAuctionJpaRepository.findByFarmAuctions(farmUserId, pageable);
        return farmAuctionByUser;
    }

    public List<FarmApplyByUser> readFarmApplyPosts(String userEmail, Pageable pageable) {
        User getUser = getUser(userEmail);
        return applyJpaRepository.findByUser(pageable, getUser).stream()
                .map(apply -> MypageFactory.from(apply))
                .collect(Collectors.toList());
    }

    private User getUser(String userEmail) {
        return userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                            AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }

}
