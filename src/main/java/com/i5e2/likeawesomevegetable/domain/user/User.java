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

    @Column(name = "email")
    private String email;

    // enum type
    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "manager_name")
    private String managerName; // 담당자 이름

    @Column(name = "phone_no")
    private String phoneNo; // 담당자 전화번호

    @Column(name = "address", length = 300)
    private String address;

    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private String registeredAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private String modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @OneToOne
    @JoinColumn(name = "farm_user_id")
    private FarmUser farmUser;

}
