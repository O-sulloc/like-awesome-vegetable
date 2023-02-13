package com.i5e2.likeawesomevegetable.alarm.dto;

import com.i5e2.likeawesomevegetable.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {

    private Long id;
    private Long userId;
    private Long senderId;
    private AlarmDetail alarmDetail;
    private Long alarmTriggerId;
    private Boolean alarmRead;

    public AlarmResponse(Alarm alarm) {
        this.alarmDetail = alarm.getAlarmDetail();
        this.alarmRead = alarm.getAlarmRead();
        this.alarmTriggerId = alarm.getAlarmTriggerId();
        this.id = alarm.getId();
        this.userId = alarm.getUser().getId();
        this.senderId = alarm.getAlarmSenderId();
    }
}
