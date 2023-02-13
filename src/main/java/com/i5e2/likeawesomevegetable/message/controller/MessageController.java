package com.i5e2.likeawesomevegetable.message.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.message.dto.MessageGetResponse;
import com.i5e2.likeawesomevegetable.message.dto.MessageSendRequest;
import com.i5e2.likeawesomevegetable.message.dto.MessageSendResponse;
import com.i5e2.likeawesomevegetable.message.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api("User Message Controller")
@RequestMapping("api/v1/user/message")
public class MessageController {
    private final MessageService messageService;

    @ApiOperation(
            value = "쪽지 작성",
            notes = "상대 회원에게 쪽지를 작성한다.")
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

    @ApiOperation(
            value = "받은 쪽지 목록 조회",
            notes = "로그인한 회원이 받은 쪽지 목록을 조회한다.")
    @GetMapping("/get-messages")
    public ResponseEntity<Result<Page<MessageGetResponse>>> showGetMessages(Authentication authentication,
                                                                            @PageableDefault(sort = "messageChecked",
                                                                                    direction = Sort.Direction.DESC)
                                                                            Pageable pageable) {
        Result<Page<MessageGetResponse>> getMessagesResponse = messageService.showGetMessages(authentication.getName(), pageable);
        return ResponseEntity.ok().body(getMessagesResponse);
    }

    @ApiOperation(
            value = "보낸 쪽지 목록 조회",
            notes = "로그인한 회원이 받은 보낸 목록을 조회한다.")
    @GetMapping("/send-messages")
    public ResponseEntity<Result<Page<MessageGetResponse>>> showSendMessages(Authentication authentication,
                                                                             @PageableDefault(sort = "messageChecked",
                                                                                     direction = Sort.Direction.DESC)
                                                                             Pageable pageable) {
        Result<Page<MessageGetResponse>> sendMessageResponse = messageService.showSendMessages(authentication.getName(), pageable);
        return ResponseEntity.ok().body(sendMessageResponse);
    }

    @ApiOperation(
            value = "쪽지 1건 조회",
            notes = "로그인한 회원이 받은 쪽지 1개를 조회한다.")
    @GetMapping("get-messages/{messageId}")
    public ResponseEntity<Result<MessageGetResponse>> showOneGetMessages(@PathVariable("messageId") Long messageId,
                                                                         Authentication authentication) {
        Result<MessageGetResponse> getMessageResponse = messageService.showOneMessage(messageId, authentication.getName());
        return ResponseEntity.ok().body(getMessageResponse);
    }

}
