package com.i5e2.likeawesomevegetable.domain.message;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_message_content")
public class MessageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_content_id")
    private Long id;

    @Column(name = "message_content", length = 200)
    private String messageContent;
}
