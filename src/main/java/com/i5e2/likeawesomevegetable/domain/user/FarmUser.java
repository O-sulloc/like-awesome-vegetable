package com.i5e2.likeawesomevegetable.domain.user;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_farm_user")
public class FarmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_user_id")
    private Long id;

    @Column(name = "farm_owner_name")
    private String farmOwnerName;

    @Column(name = "major_product")
    private String majorProduct;

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "farm_ground_area")
    private String farmGroundArea;

    @Column(name = "farm_facility_area")
    private String farmFacilityArea;

    @Column(name = "farm_website")
    private String farmWebsite;

    @Column(name = "farm_info")
    private String farmInfo;
}
