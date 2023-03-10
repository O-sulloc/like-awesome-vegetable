package com.i5e2.likeawesomevegetable.message.service;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.message.Message;
import com.i5e2.likeawesomevegetable.message.MessageContent;
import com.i5e2.likeawesomevegetable.message.dto.MessageGetResponse;
import com.i5e2.likeawesomevegetable.message.dto.MessageSendResponse;
import com.i5e2.likeawesomevegetable.message.dto.MessageType;
import com.i5e2.likeawesomevegetable.message.repository.MessageContentJpaRepository;
import com.i5e2.likeawesomevegetable.message.repository.MessageJpaRepository;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MessageService {
    private final UserJpaRepository userJpaRepository;
    private final MessageJpaRepository messageJpaRepository;
    private final MessageContentJpaRepository messageContentJpaRepository;

    /*     쪽지 작성     */
    public Result<MessageSendResponse> sendMessage(String toUserEmail, String content, String loginEmail) {
        User loginUser = validateUser(loginEmail);
        User toUser = validateUser(toUserEmail);

        // 본인에게 쪽지 전송 불가능
        if (loginUser.equals(toUser)) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_GETTER, AppErrorCode.INVALID_GETTER.getMessage());
        }

        // 쪽지 내용 저장
        MessageContent messageContent = MessageContent.makeMessageContent(content);
        messageContentJpaRepository.save(messageContent);

        // 송, 수신 개별 저장
        Message sendMessage = Message.makeMessage(messageContent, loginUser, toUserEmail, MessageType.SEND);
        Message getMessage = Message.makeMessage(messageContent, toUser, loginUser.getEmail(), MessageType.GET);
        messageJpaRepository.save(sendMessage);
        messageJpaRepository.save(getMessage);

        MessageSendResponse messageSendResponse = MessageSendResponse.of(sendMessage);

        return Result.success(messageSendResponse);
    }

    /*     받은 쪽지 목록 조회     */
    @Transactional(readOnly = true)
    public Result<Page<MessageGetResponse>> showGetMessages(String loginEmail, Pageable pageable) {
        User loginUser = validateUser(loginEmail);

        // 로그인 유저, MessageType 수신으로 조회
        Page<Message> getMessages = messageJpaRepository.findByUserAndMessageType(loginUser, MessageType.GET, pageable);
        if (!getMessages.hasContent()) {
            throw new AwesomeVegeAppException(AppErrorCode.GET_MESSAGE_NOT_FOUND, AppErrorCode.GET_MESSAGE_NOT_FOUND.getMessage());
        }
        Page<MessageGetResponse> userMessageResult = getMessages.map(
                message -> MessageGetResponse.of(message)
        );
        return Result.success(userMessageResult);
    }

    /*     보낸 쪽지 목록 조회     */
    @Transactional(readOnly = true)
    public Result<Page<MessageGetResponse>> showSendMessages(String loginEmail, Pageable pageable) {
        User loginUser = validateUser(loginEmail);

        // 로그인 유저, MessageType 송신으로 조회
        Page<Message> getMessages = messageJpaRepository.findByUserAndMessageType(loginUser, MessageType.SEND, pageable);
        if (!getMessages.hasContent()) {
            throw new AwesomeVegeAppException(AppErrorCode.SEND_MESSAGE_NOT_FOUND, AppErrorCode.SEND_MESSAGE_NOT_FOUND.getMessage());
        }
        Page<MessageGetResponse> userMessageResult = getMessages.map(
                message -> MessageGetResponse.of(message)
        );
        return Result.success(userMessageResult);
    }

    /*     쪽지 1개 조회     */
    public Result<MessageGetResponse> showOneMessage(Long messageId, String loginEmail) {
        User loginUser = validateUser(loginEmail);
        Message getMessage = checkMessagePresents(messageId, loginUser);

        // message 읽음으로 등록
        getMessage.makeMessageChecked();
        messageJpaRepository.save(getMessage);

        MessageGetResponse messageGetResponse = MessageGetResponse.of(getMessage);

        return Result.success(messageGetResponse);
    }

    // JWT로 로그인 유저 확인
    private User validateUser(String userEmail) {
        User loginUser = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.EMAIL_NOT_FOUND,
                        AppErrorCode.EMAIL_NOT_FOUND.getMessage())
                );
        return loginUser;
    }

    // 해당 번호의 message 존재 유무 확인
    private Message checkMessagePresents(Long messageId, User loginUser) {
        Message getMessage = messageJpaRepository.findByIdAndUser(messageId, loginUser)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.MESSAGE_NOT_FOUND,
                        AppErrorCode.MESSAGE_NOT_FOUND.getMessage())
                );
        return getMessage;
    }
}
