package com.i5e2.likeawesomevegetable.user.admin;

import com.i5e2.likeawesomevegetable.user.basic.dto.UserType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "t_admin")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_type")
    private UserType adminType;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_phone_no")
    private String adminPhoneNo;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_nickname")
    private String adminNickname;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "admin_registered_at")
    private LocalDateTime adminRegisteredAt;

    @Column(name = "admin_modified_at")
    private LocalDateTime adminModifiedAt;

    @Column(name = "admin_deleted_at")
    private LocalDateTime adminDeletedAt;

    @Builder
    public AdminUser(UserType adminType, String adminName, String adminPhoneNo, String adminEmail, String adminNickname
            , String adminPassword) {
        this.adminType = adminType;
        this.adminName = adminName;
        this.adminPhoneNo = adminPhoneNo;
        this.adminEmail = adminEmail;
        this.adminNickname = adminNickname;
        this.adminPassword = adminPassword;
        this.adminRegisteredAt = LocalDateTime.now();
    }
}
