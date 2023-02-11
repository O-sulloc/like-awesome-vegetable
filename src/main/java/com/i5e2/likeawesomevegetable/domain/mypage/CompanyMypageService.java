package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBiddingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.CompanyBuyingByUser;
import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypageFactory;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
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

    public List<CompanyBuyingByUser> readCompanyBuyingPosts(String userEmail, Pageable pageable) {
        Long companyUserId = getUser(userEmail).getCompanyUser().getId();
        return companyBuyingJpaRepository.findByCompanyBuyings(companyUserId, pageable);
    }

    public List<CompanyBiddingByUser> readCompanyBiddingPosts(String userEmail, Pageable pageable) {
        User getUser = getUser(userEmail);
        return standByJpaRepository.findByUser(getUser, pageable).stream()
                .map(standby -> MypageFactory.from(standby))
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
