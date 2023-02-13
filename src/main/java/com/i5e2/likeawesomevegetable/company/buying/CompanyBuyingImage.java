package com.i5e2.likeawesomevegetable.company.buying;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_company_buying_image")
public class CompanyBuyingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_buying_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_buying_id")
    private CompanyBuying companyBuying;

    @Column(name = "buying_image_link")
    private String buyingImageLink;

    @Column(name = "buying_image_name")
    private String buyingImageName;

}
