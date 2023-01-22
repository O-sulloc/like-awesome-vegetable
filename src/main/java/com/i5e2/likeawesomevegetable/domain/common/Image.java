package com.i5e2.likeawesomevegetable.domain.common;

import com.i5e2.likeawesomevegetable.domain.market.Auction;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="t_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    // enum type
    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

}
