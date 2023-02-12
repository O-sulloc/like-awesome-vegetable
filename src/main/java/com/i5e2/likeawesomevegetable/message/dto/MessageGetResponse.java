package com.i5e2.likeawesomevegetable.message.dto;

import com.i5e2.likeawesomevegetable.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageGetResponse {
    private String loginUser;
    private String otherUser;
    private MessageType messageType;
    private String contents;
    private MessageChecked messageChecked;
    private String messageSendTime;

    public static MessageGetResponse of(Message message) {
        return MessageGetResponse.builder()
                .loginUser(message.getUser().getEmail())
                .otherUser(message.getOtherUser())
                .messageType(message.getMessageType())
                .contents(message.getMessageContent().getMessageContent())
                .messageChecked(message.getMessageChecked())
                .messageSendTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(message.getMessageRegisteredAt()))
                .build();
    }

}
