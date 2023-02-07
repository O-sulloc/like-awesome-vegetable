package com.i5e2.likeawesomevegetable.domain.contract;

import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@Table(name = "t_contract_info")
public class ContractInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id")
    private String documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminUser adminUser;

    @Column(name = "buyer_name", length = 30)
    private String buyerName;

    @Column(name = "buyer_id", length = 30)
    private String buyerId;

    @Column(name = "buyer_phone_no", length = 50)
    private String buyerPhoneNo;

    @Column(name = "seller_name", length = 30)
    private String sellerName;

    @Column(name = "seller_id", length = 30)
    private String sellerId;

    @Column(name = "seller_phone_no", length = 50)
    private String sellerPhoneNo;

    @Column(name = "contract_total_price")
    private Long contractTotalPrice;

    @Column(name = "contract_item", length = 30)
    private String contractItem;

    @Column(name = "contract_quantity")
    private Long contractQuantity;

    @Column(name = "contract_date")
    private String contractDate;

    @Column(name = "farm_address", length = 300)
    private String farmAddress;

    @Column(name = "buyer_address", length = 300)
    private String buyerAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status")
    private ContractStatus contractStatus;

    @Column(name = "account_no", length = 30)
    private String accountNo;

    @Column(name = "bank_name", length = 30)
    private String bankName;

    @Column(name = "account_owner_name", length = 30)
    private String accountOwnerName;

    public ContractInfo(String documentId, String buyerId, String sellerId, String contractItem, Long contractQuantity,
                        Long finalPrice) {
        this.documentId = documentId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.contractItem = contractItem;
        this.contractQuantity = contractQuantity;
        this.contractStatus = ContractStatus.SIGN;
        this.contractTotalPrice = finalPrice;
    }

    public void updateContractInfo(String buyerAddress, String buyerName, String buyerPhoneNo, String accountNo, String bankName, String accountOwnerName,
                                   String farmAddress, String sellerName, String sellerPhoneNo, String contractDate) {
        this.buyerName = buyerName;
        this.buyerPhoneNo = buyerPhoneNo;
        this.sellerName = sellerName;
        this.sellerPhoneNo = sellerPhoneNo;
        this.contractDate = contractDate;
        this.farmAddress = farmAddress;
        this.buyerAddress = buyerAddress;
        this.contractStatus = ContractStatus.COMPLETED;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.accountOwnerName = accountOwnerName;
    }


}
