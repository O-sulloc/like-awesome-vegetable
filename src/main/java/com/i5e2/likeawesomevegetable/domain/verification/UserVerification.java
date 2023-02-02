package com.i5e2.likeawesomevegetable.domain.verification;

import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_user_verification")
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_verification_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "verification_email", length = 30)
    private Verification verificationEmail;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "verification_url", length = 30)
    private Verification verificationUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "verification_business", length = 30)
    private Verification verificationBusiness;

    public void makeEmailVerification(Verification verificationEnum) {
        this.verificationEmail = verificationEnum;
    }

    public void makeUrlVerification(Verification verificationEnum) {
        this.verificationUrl = verificationEnum;
    }

    public void makeBusinessVerification(Verification verificationEnum) {
        this.verificationBusiness = verificationEnum;
    }

}
