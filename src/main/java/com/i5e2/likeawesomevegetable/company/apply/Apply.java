package com.i5e2.likeawesomevegetable.company.apply;

import com.i5e2.likeawesomevegetable.board.post.dto.ParticipationStatus;
import com.i5e2.likeawesomevegetable.company.buying.CompanyBuying;
import com.i5e2.likeawesomevegetable.user.basic.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "buying_status")
    private ParticipationStatus participationStatus;

    @Column(name = "apply_quantity")
    private Long applyQuantity;

    @CreatedDate
    @Column(name = "apply_time")
    private LocalDateTime applyTime;

    @Column(name = "buying_title", length = 20)
    private String buyingTitle;

    @Column(name = "apply_number", length = 100)
    private String applyNumber;

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber;
    }

    public void updateStatusToEnd() {
        this.participationStatus = ParticipationStatus.END;
    }
}