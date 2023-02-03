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

    @OneToOne
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @OneToOne
    @JoinColumn(name = "farm_user_id")
    private FarmUser farmUser;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 50)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type", length = 20)
    private UserType userType;

    @Column(name = "manager_name", length = 10)
    private String managerName; // 담당자 이름

    @Column(name = "manager_phone_no", length = 50)
    private String manaverPhoneNo; // 담당자 전화번호

    @CreatedDate
    @Column(name = "user_registered_at", updatable = false)
    private LocalDateTime userRegisteredAt;

    @LastModifiedDate
    @Column(name = "user_modified_at")
    private LocalDateTime userModifiedAt;

    @Column(name = "user_deleted_at")
    private LocalDateTime userDeletedAt;

    public void updateUserType(UserType userType) {
        this.userType = userType;
    }

    public void updateCompanyUser(CompanyUser companyUser) {
        this.companyUser = companyUser;
    }

    public void updateFarmUser(FarmUser farmUser) {
        this.farmUser = farmUser;
    }

}
