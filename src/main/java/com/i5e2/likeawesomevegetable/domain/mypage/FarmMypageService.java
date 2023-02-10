package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypageFactory;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.UserException;
import com.i5e2.likeawesomevegetable.repository.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
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
        Long userId = getUser(userEmail).getId();
        List<FarmAuctionByUser> farmAuctionByUser = farmAuctionJpaRepository.findByFarmAuctions(userId, pageable);
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
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND,
                            UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }

}
