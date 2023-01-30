package com.i5e2.likeawesomevegetable.domain.alarm;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="t_alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "alarm_sender_id")
    private Long alarmSenderId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "alarm_detail")
    private AlarmDetail alarmDetail;

    @Column(name = "alarm_trigger_id")
    private Long alarmTriggerId;

    @Column(name = "alarm_read")
    private Boolean alarmRead;

}
