package com.i5e2.likeawesomevegetable.domain.mypage;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmApplyByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.FarmApplyJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmMypageJapRepository;
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
    private final FarmMypageJapRepository farmMypageJapRepository;
    private final FarmApplyJpaRepository farmApplyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<FarmAuctionByUser> readFarmActionByUser(Pageable pageable, Long userId) {
        List<FarmAuctionByUser> farmAuctionByUser = farmMypageJapRepository.findByFarmAuctions(userId, pageable);
        return farmAuctionByUser;
    }

    public List<FarmApplyByUser> readFarmApplyPosts(Long userId, Pageable pageable) {
        User getUser = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다"));
        return farmApplyJpaRepository.findByUser(pageable, getUser).stream()
                .map(apply -> FarmMypageFactory.from(apply))
                .collect(Collectors.toList());
    }
}
