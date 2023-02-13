package com.i5e2.likeawesomevegetable.farm.auction.service;

import com.i5e2.likeawesomevegetable.user.farm.FarmUser;
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
@Table(name = "t_farm_image")
public class FarmImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_user_id")
    private FarmUser farmUser;

    //    @Column(name = "link")
//    private String link;
    @Column(name = "farm_image_link")
    private String farmImageLink;

    // 추가
    @Column(name = "farm_image_name")
    private String farmImageName;

    @CreatedDate
    @Column(name = "farm_image_registered_at")
    private LocalDateTime farmImageRegisteredAt;

    @LastModifiedDate
    @Column(name = "farm_image_modified_at")
    private LocalDateTime farmImageModifiedAt;

    public static FarmImage makeFarmImage(String farmImageName, String farmImageLink, FarmUser farmUser) {
        return FarmImage.builder()
                .farmImageName(farmImageName)
                .farmImageLink(farmImageLink)
                .farmUser(farmUser)
                .build();
    }
}