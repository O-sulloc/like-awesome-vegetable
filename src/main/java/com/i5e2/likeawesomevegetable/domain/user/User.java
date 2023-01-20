package com.i5e2.likeawesomevegetable.domain.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "business_name")
    private String businessName; // 업체 이름

    @Column(name = "manger_name")
    private String managerName; // 담당자 이름

    @Column(name = "onwer_name")
    private String OwnerName; // 대표자 이름

    @Column(name = "business_phone_no")
    private String BusinessPhoneNo; // 업체 전화번호

    @Column(name = "phone_no")
    private String phoneNo; // 담당자 전화번호

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "email")
    private String email;

    // enum type
    @Column(name = "verification")
    private String verification;

    @Column(name = "charged_point")
    private Long chargedPoint;

    @Column(name = "business_no")
    private String businessNo;

    @Column(name = "farmer_no")
    private String farmerNo;

    @Column(name = "land")
    private Long land;

    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private String registeredAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private String modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
