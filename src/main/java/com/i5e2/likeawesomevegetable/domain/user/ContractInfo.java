package com.i5e2.likeawesomevegetable.domain.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_contract_info")
public class ContractInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "contract_buyer", length = 10)
    private String contractBuyer;

    @Column(name = "contract_seller", length = 10)
    private String contractSeller;

    @Column(name = "contract_total_price")
    private Long contractTotalPrice;

    @Column(name = "contract_item", length = 30)
    private String contractItem;

    @Column(name = "contract_quantity")
    private Long contractQuantity;

    @Column(name = "contract_date")
    private LocalDateTime contractDate;

    @Column(name = "contract_law", length = 1000)
    private String contractLaw;

    @Column(name = "buyer_phone_no", length = 50)
    private String buyerPhoneNo;

    @Column(name = "seller_phone_no", length = 50)
    private String sellerPhoneNo;

    @Column(name = "farm_address", length = 300)
    private String farmAddress;

    @Column(name = "shipping_start_date")
    private LocalDateTime shippingStartDate;

    @Column(name = "buyer_address", length = 300)
    private String buyerAddress;
}
