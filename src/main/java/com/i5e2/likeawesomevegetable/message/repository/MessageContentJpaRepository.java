package com.i5e2.likeawesomevegetable.message.repository;

import com.i5e2.likeawesomevegetable.message.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageContentJpaRepository extends JpaRepository<MessageContent, Long> {
}
