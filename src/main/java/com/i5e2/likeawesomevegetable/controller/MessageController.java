package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.message.MessageService;
import com.i5e2.likeawesomevegetable.domain.message.dto.MessageGetResponse;
import com.i5e2.likeawesomevegetable.domain.message.dto.MessageSendRequest;
import com.i5e2.likeawesomevegetable.domain.message.dto.MessageSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    /*     쪽지 작성     */
    @PostMapping("/new-message")
    public ResponseEntity<Result<MessageSendResponse>> newSendMessage(@RequestBody MessageSendRequest messageSendRequest,
                                                                      Authentication authentication) {
        Result<MessageSendResponse> newMessageResponse = messageService.sendMessage(
                messageSendRequest.getToUser(),
                messageSendRequest.getContent(),
                authentication.getName()
        );
        return ResponseEntity.ok().body(newMessageResponse);
    }

    /*     받은 쪽지 목록 조회     */
    @GetMapping("/get-messages")
    public ResponseEntity<Result<Page<MessageGetResponse>>> showGetMessages(Authentication authentication,
                                                                            @PageableDefault(sort = "messageChecked",
                                                                                    direction = Sort.Direction.DESC)
                                                                            Pageable pageable) {
        Result<Page<MessageGetResponse>> getMessagesResponse = messageService.showGetMessages(authentication.getName(), pageable);
        return ResponseEntity.ok().body(getMessagesResponse);
    }

    /*     보낸 쪽지 목록 조회     */
    @GetMapping("/send-messages")
    public ResponseEntity<Result<Page<MessageGetResponse>>> showSendMessages(Authentication authentication,
                                                                             @PageableDefault(sort = "messageChecked",
                                                                                     direction = Sort.Direction.DESC)
                                                                             Pageable pageable) {
        Result<Page<MessageGetResponse>> sendMessageResponse = messageService.showSendMessages(authentication.getName(), pageable);
        return ResponseEntity.ok().body(sendMessageResponse);
    }

    /*     쪽지 1개 조회     */
    @GetMapping("get-messages/{messageId}")
    public ResponseEntity<Result<MessageGetResponse>> showOneGetMessages(@PathVariable("messageId") Long messageId,
                                                                         Authentication authentication) {
        Result<MessageGetResponse> getMessageResponse = messageService.showOneMessage(messageId, authentication.getName());
        return ResponseEntity.ok().body(getMessageResponse);
    }

}
