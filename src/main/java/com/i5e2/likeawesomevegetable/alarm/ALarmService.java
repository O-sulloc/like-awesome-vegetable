package com.i5e2.likeawesomevegetable.alarm;

import com.i5e2.likeawesomevegetable.alarm.dto.AlarmResponse;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ALarmService {

    private final UserJpaRepository userJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;

    public List<AlarmResponse> getAlarms(String email) {
        Optional<User> user = userJpaRepository.findByEmail(email);
        user.orElseThrow(
                () -> new AwesomeVegeAppException(AppErrorCode.LOGIN_USER_NOT_FOUND, AppErrorCode.LOGIN_USER_NOT_FOUND.getMessage())
        );
        List<Alarm> list = alarmJpaRepository.findAllByUserId(user.get().getId());
        List<AlarmResponse> alarmList = list.stream()
                .map(AlarmResponse::new).collect(Collectors.toList());
        return alarmList;
    }
}
