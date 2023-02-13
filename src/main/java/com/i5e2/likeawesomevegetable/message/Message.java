package com.i5e2.likeawesomevegetable.message;

import com.i5e2.likeawesomevegetable.message.dto.MessageChecked;
import com.i5e2.likeawesomevegetable.message.dto.MessageType;
import com.i5e2.likeawesomevegetable.user.basic.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_content_id")
    private MessageContent messageContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "other_id")
    private String otherUser;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "message_type", length = 20)
    private MessageType messageType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "message_checked", length = 20)
    private MessageChecked messageChecked;

    @Column(name = "message_registered_at")
    private LocalDateTime messageRegisteredAt;

    public static Message makeMessage(MessageContent messageContent, User loginUser, String toUserEmail, MessageType messageType) {
        return Message.builder()
                .messageContent(messageContent)
                .user(loginUser)
                .otherUser(toUserEmail)
                .messageType(messageType)
                .messageChecked(MessageChecked.MESSAGE_UNCHECKED)
                .messageRegisteredAt(LocalDateTime.now())
                .build();
    }

    // 확인된 쪽지 상태 변경
    public void makeMessageChecked() {
        this.messageChecked = MessageChecked.MESSAGE_CHECKED;
    }
}
