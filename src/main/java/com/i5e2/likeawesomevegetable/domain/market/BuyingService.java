package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.alarm.Alarm;
import com.i5e2.likeawesomevegetable.domain.alarm.AlarmDetail;
import com.i5e2.likeawesomevegetable.domain.apply.Apply;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserId;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.AlarmJpaRepository;
import com.i5e2.likeawesomevegetable.repository.ApplyJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyBuyingJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final CompanyBuyingJpaRepository buyingJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompanyBuyingJpaRepository companyBuyingJpaRepository;
    private final ApplyJpaRepository applyJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;

    public BuyingResponse creatBuying(BuyingRequest buyingRequest, String email) {
        User user = userJpaRepository.findByEmail(email).get();
        CompanyUser companyUser = user.getCompanyUser();

        notValidCompanyUser(companyUser);

        CompanyBuying companyBuying = buyingRequest.toEntity(buyingRequest, companyUser);
        buyingJpaRepository.save(companyBuying);

        BuyingResponse buyingResponse = BuyingResponse.builder()
                .buyingId(companyBuying.getId())
                .message("모집 게시글 작성 완료")
                .build();
        return buyingResponse;
    }


    private void notValidCompanyUser(CompanyUser companyUser) {
        if (companyUser == null) {
            throw new AwesomeVegeAppException(
                    AppErrorCode.COMPANY_USER_NOT_FOUND,
                    AppErrorCode.COMPANY_USER_NOT_FOUND.getMessage()
            );
        }
    }

    // 모집 종료
    public void applyEnd(Long companyBuyingId) {
        CompanyBuying companyBuying = companyBuyingJpaRepository.findById(companyBuyingId)
                .orElseThrow(() -> new AwesomeVegeAppException(AppErrorCode.POST_NOT_FOUND, AppErrorCode.POST_NOT_FOUND.getMessage()));

        applyJpaRepository.findAllByCompanyBuyingId(companyBuyingId).forEach(Apply::updateStatusToEnd);

        companyBuying.updateStatusToEnd();
        // TODO - alarm
        List<UserId> list = applyJpaRepository.selectByCompanyBuyingId(companyBuyingId);
        for (int i = 0; i < list.size(); i++) {
            Alarm alarm = Alarm.builder()
                    .alarmDetail(AlarmDetail.BUYING)
                    .alarmTriggerId(companyBuying.getId())
                    .alarmRead(Boolean.FALSE)
                    .alarmSenderId(companyBuying.getCompanyUser().getId())
                    .user(userJpaRepository.findById(list.get(i).getUserId()).get())
                    .build();
            alarmJpaRepository.save(alarm);
        }
    }
}
