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

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "content")
    private String content;

    // enum type
    @Column(name = "post_type")
    private String postType;

}
