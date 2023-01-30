package com.i5e2.likeawesomevegetable.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_company_user")
public class CompanyFile {

    @Id
    @Column(name = "company_file_id")
    private Long id;

    @Column(name = "company_user_id")
    private Long companyUserId;

    @Column(name = "company_file_link", length = 300)
    private String companyFileLink;

    @Column(name = "company_file_name", length = 100)
    private String companyFileName;

    @Column(name = "company_file_registered_at")
    private LocalDateTime companyFileRegisteredAt;

    @Column(name = "company_file_modified_at")
    private LocalDateTime companyFileModifiedAt;

}
