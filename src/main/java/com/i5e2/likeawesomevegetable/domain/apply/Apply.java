package com.i5e2.likeawesomevegetable.domain.apply;

import com.i5e2.likeawesomevegetable.domain.market.CompanyBuying;
import com.i5e2.likeawesomevegetable.domain.market.CompanyBuyingStatus;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_buying_id")
    private CompanyBuying companyBuying;

    @Enumerated(EnumType.STRING)
    @Column(name = "buying_status")
    private CompanyBuyingStatus companyBuyingStatus;

    @Column(name = "apply_quantity")
    private Long applyQuantity;

    @Column(name = "apply_time")
    private LocalDateTime applyTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_result")
    private ApplyResult applyResult;

    @Column(name = "buying_title", length = 20)
    private String buyingTitle;

    @Column(name = "apply_number", length = 100)
    private String applyNumber;

}