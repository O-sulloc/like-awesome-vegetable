package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.message.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageContentJpaRepository extends JpaRepository<MessageContent, Long> {
}
