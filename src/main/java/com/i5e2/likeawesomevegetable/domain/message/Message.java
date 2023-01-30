package com.i5e2.likeawesomevegetable.domain.message;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "message_content_id")
    private MessageContent messageContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "other_id")
    private Long otherId;

    @Column(name = "message_type", length = 20)
    private MessageType messageType;
}
