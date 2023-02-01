package com.i5e2.likeawesomevegetable.domain.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_company_image")
public class CompanyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @Column(name = "company_image_link", length = 300)
    private String companyImageLink;

    @Column(name = "company_image_name", length = 100)
    private String companyImageName;

    @CreatedDate
    @Column(name = "company_image_registered_at")
    private LocalDateTime companyImageRegisteredAt;

    @LastModifiedDate
    @Column(name = "company_image_modified_at")
    private LocalDateTime companyImageModifiedAt;

    public static CompanyImage makeCompanyImage(String companyImageName, String companyImageLink, CompanyUser companyUser) {
        return CompanyImage.builder()
                .companyImageName(companyImageName)
                .companyImageLink(companyImageLink)
                .companyUser(companyUser)
                .build();
    }

}
