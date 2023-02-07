package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.message.Message;
import com.i5e2.likeawesomevegetable.domain.message.MessageType;
import com.i5e2.likeawesomevegetable.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {
    // MessageType과 로그인 유저로 쪽지 필터
    Page<Message> findByUserAndMessageType(User user, MessageType messageType, Pageable pageable);

    Optional<Message> findByIdAndUser(Long messageId, User user);
}
