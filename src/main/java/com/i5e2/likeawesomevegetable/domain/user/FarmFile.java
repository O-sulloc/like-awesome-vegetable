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

    @Column(name = "farm_file_registered_at")
    private LocalDateTime farmFileRegisteredAt;

    @Column(name = "farm_file_modified_at")
    private LocalDateTime farmFileModifiedAt;
}