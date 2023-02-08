package com.i5e2.likeawesomevegetable.domain.mypage;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.FarmAuctionByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypageFactory;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.StandByJpaRepository;
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
public class CompanyMypageService {

    private final StandByJpaRepository standByJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<FarmAuctionByUser> readCompanyBuyingPosts(Long userId, Pageable pageable) {
        return companyBuyingJpaRepository.findByCompanyBuyings(userId, pageable);
    }

    public List<CompanyBiddingByUser> readCompanyBiddingPosts(Long userId, Pageable pageable) {
        User getUser = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다"));
        return standByJpaRepository.findByUser(getUser, pageable).stream()
                .map(standby -> MypageFactory.from(standby))
                .collect(Collectors.toList());
    }
}
