package com.i5e2.likeawesomevegetable.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_farm_user")
public class FarmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_user_id")
    private Long id;

    @Column(name = "farm_owner_name")
    private String farmOwnerName;

    @Column(name = "farm_major_item")
    private String farmMajorItem;

    @Column(name = "farm_type")
    private String farmType;

    @Column(name = "ground_area")
    private String groundArea;

    @Column(name = "facility_area")
    private String facilityArea;

    @Column(name = "farm_website")
    private String farmWebsite;

    @Column(name = "farm_info", length = 500)
    private String farmInfo;

    @Column(name = "farm_name")
    private String farmName;

    // 추가
    @Column(name = "farm_address")
    private String farmAddress;

    // 추가
    @Column(name = "farm_registered_at")
    private LocalDateTime farmRegisteredAt;
}