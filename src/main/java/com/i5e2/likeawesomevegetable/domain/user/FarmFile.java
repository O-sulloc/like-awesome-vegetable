package com.i5e2.likeawesomevegetable.domain.user;

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
@Table(name = "t_farm_file")
public class FarmFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_user_id")
    private FarmUser farmUser;

    @Column(name = "farm_file_link")
    private String farmFileLink;

    @Column(name = "farm_file_name")
    private String farmFileName;

    @CreatedDate
    @Column(name = "farm_file_registered_at")
    private LocalDateTime farmFileRegisteredAt;

    @LastModifiedDate
    @Column(name = "farm_file_modified_at")
    private LocalDateTime farmFileModifiedAt;

    public static FarmFile makeFarmFile(String farmFileName, String farmFileLink, FarmUser farmUser) {
        return FarmFile.builder()
                .farmFileName(farmFileName)
                .farmFileLink(farmFileLink)
                .farmUser(farmUser)
                .build();
    }

}