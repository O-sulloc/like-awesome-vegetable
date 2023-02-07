package com.i5e2.likeawesomevegetable.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageSendRequest {
    private String toUser;
    private String content;
}
