package com.i5e2.likeawesomevegetable.domain.user;

import com.i5e2.likeawesomevegetable.domain.verification.dto.VerifyCompanyUserRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_company_user")
public class CompanyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_user_id")
    private Long id;

    @Column(name = "company_name", length = 50)
    private String companyName;

    @Column(name = "company_phone_no", length = 50)
    private String companyPhoneNo;

    @Column(name = "company_website", length = 50)
    private String companyWebsite;

    @Column(name = "company_owner_name", length = 50)
    private String companyOwnerName;

    @Column(name = "company_business_no", length = 50)
    private String companyBusinessNo;

    @Column(name = "company_open_date", length = 50)
    private String companyOpenDate;

    @Column(name = "company_info", length = 500)
    private String companyInfo;

    @Column(name = "company_line_info", length = 100)
    private String companyLineInfo;

    @Column(name = "company_registered_at")
    private LocalDateTime companyRegisteredAt;

    @Column(name = "company_major_item")
    private Integer companyMajorItem;

    public static CompanyUser makeCompanyUser(VerifyCompanyUserRequest verifyCompanyUserRequest) {
        return CompanyUser.builder()
                .companyName(verifyCompanyUserRequest.getCompanyName())
                .companyPhoneNo(verifyCompanyUserRequest.getCompanyPhoneNo())
                .companyWebsite(verifyCompanyUserRequest.getCompanyWebsite())
                .companyOwnerName(verifyCompanyUserRequest.getCompanyOwnerName())
                .companyBusinessNo(verifyCompanyUserRequest.getCompanyBusinessNo())
                .companyOpenDate(verifyCompanyUserRequest.getCompanyOpenDate())
                .companyInfo(verifyCompanyUserRequest.getCompanyInfo())
                .companyLineInfo(verifyCompanyUserRequest.getCompanyLineInfo())
                .companyRegisteredAt(LocalDateTime.now())
                .companyMajorItem(verifyCompanyUserRequest.getCompanyMajorItem())
                .build();
    }
}
