package com.i5e2.likeawesomevegetable.domain.mypage;

import com.i5e2.likeawesomevegetable.domain.mypage.dto.MypagePointEvenLogResponse;
import com.i5e2.likeawesomevegetable.domain.point.PointFactory;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageApiService {
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    public List<MypagePointEvenLogResponse> readUserPointLogs(Long id) {
        return pointEventLogJpaRepository.getPointUserId(id).stream()
                .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                .collect(Collectors.toList());
    }

    public List<MypagePointEvenLogResponse> readAdminPointLogs(Long id) {
        return pointEventLogJpaRepository.getPointAdminId(id).stream()
                .map(pointEventLog -> PointFactory.createUserPointEventLog(pointEventLog))
                .collect(Collectors.toList());
    }
}
