package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @Column(name = "buying_title", length = 20)
    private String buyingTitle;

    //농산물 API 부류 코드
    //category -> buyingItemCategory
    @Column(name = "buying_item_category")
    private Integer buyingItemCategory;

    //농산물 API 품목 코드
    //item -> buyingItem
    @Column(name = "buying_item")
    private Integer buyingItem;

    //quantity -> buyingQuantity
    @Column(name = "buying_quantity")
    private Integer buyingQuantity;

    //price -> buyingPrice
    @Column(name = "buying_price")
    private Integer buyingPrice;

    //    @Column(name = "start_time")
//    private String startTime;
//    @Column(name = "end_time")
//    private String endTime;
    @Column(name = "buying_start_time")
    private LocalDateTime buyingStartTime;

    @Column(name = "buying_end_time")
    private LocalDateTime buyingEndTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "company_buying_status")
    private CompanyBuyingStatus companyBuyingStatus;

    //description -> buyingDescription
    @Column(name = "buying_description", length = 5000)
    private String buyingDescription;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "buying_shipping")
    private BuyingShipping buyingShipping;

    @Column(name = "receiver_name", length = 10)
    private String receiverName;

    @Column(name = "receiver_phone_no", length = 50)
    private String receiverPhoneNo;

    @Column(name = "receiver_address", length = 300)
    private String receiverAddress;

    //tag -> buyingTag
    @Column(name = "buying_tag", length = 50)
    private String buyingTag;

    @Column(name = "buying_registered_at")
    private LocalDateTime buyingRegisteredAt;

    @Column(name = "buying_modified_at")
    private LocalDateTime buyingModifiedAt;

    @Column(name = "buying_deleted_at")
    private LocalDateTime buyingDeletedAt;

    //BUYING-날짜-게시글번호-신청ID
    @Column(name = "buying_number")
    private String buyingNumber;

}