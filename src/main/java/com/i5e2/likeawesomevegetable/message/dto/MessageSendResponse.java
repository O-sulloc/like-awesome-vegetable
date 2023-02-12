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
public class MessageSendResponse {
    private String fromEmail;
    private String toEmail;
    private String content;
    private MessageType messageType;
    private String messageSendTime;
    private MessageChecked messageChecked;

    public static MessageSendResponse of(Message sendMessage) {
        return MessageSendResponse.builder()
                .fromEmail(sendMessage.getUser().getEmail())
                .toEmail(sendMessage.getOtherUser())
                .content(sendMessage.getMessageContent().getMessageContent())
                .messageType(sendMessage.getMessageType())
                .messageChecked(sendMessage.getMessageChecked())
                .messageSendTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(sendMessage.getMessageRegisteredAt()))
                .build();
    }
}
