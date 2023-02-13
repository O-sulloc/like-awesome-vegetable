package com.i5e2.likeawesomevegetable.company.buying;

import com.i5e2.likeawesomevegetable.user.company.CompanyUser;
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
@Table(name = "t_company_file")
public class CompanyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_user_id")
    private CompanyUser companyUser;

    @Column(name = "company_file_link", length = 300)
    private String companyFileLink;

    @Column(name = "company_file_name", length = 100)
    private String companyFileName;

    @CreatedDate
    @Column(name = "company_file_registered_at")
    private LocalDateTime companyFileRegisteredAt;

    @LastModifiedDate
    @Column(name = "company_file_modified_at")
    private LocalDateTime companyFileModifiedAt;

    public static CompanyFile makeCompanyFile(String companyFileName, String companyFileLink, CompanyUser companyUser) {
        return CompanyFile.builder()
                .companyFileName(companyFileName)
                .companyFileLink(companyFileLink)
                .companyUser(companyUser)
                .build();
    }

}