package com.i5e2.likeawesomevegetable.domain.item;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_item")
public class Item {

    @Id
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_category_code")
    private String itemCategoryCode;

    @Column(name = "item_category_name")
    private String itemCategoryName;
}
