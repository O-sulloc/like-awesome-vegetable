package com.i5e2.likeawesomevegetable.domain.user;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_company_user")
public class CompanyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_user_id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_phone_no")
    private String companyPhoneNo;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "company_owner_name")
    private String companyOwnerName;

    @Column(name = "company_business_no")
    private String companyBusinessNo;

    @Column(name = "company_open_date")
    private String companyOpenDate;

    @Column(name = "company_info", length = 500)
    private String companyInfo;

    @Column(name = "company_line_info")
    private String companyLineInfo;
}
