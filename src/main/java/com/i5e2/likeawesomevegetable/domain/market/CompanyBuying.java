package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_company_buying")
public class CompanyBuying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_buying_id")
    private Long id;

    @Column(name = "title")
    private String title;

//    @Column(name = "start_time")
//    private LocalDateTime startTime;
//    @Column(name = "end_time")
//    private LocalDateTime endTime;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;

    //카테고리 추가
    @Column(name = "category")
    private String category;

    @Column(name = "item")
    private String item;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "tag")
    private String tag;

    @Column(name = "shipping")
    @Enumerated(value = EnumType.STRING)
    private ShippingEnum shipping;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone_no")
    private String receiverPhoneNo;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}