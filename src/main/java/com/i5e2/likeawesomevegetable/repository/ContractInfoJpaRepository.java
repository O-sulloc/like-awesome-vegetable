package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.contract.ContractInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractInfoJpaRepository extends JpaRepository<ContractInfo, String> {

    Optional<ContractInfo> findByDocumentId(String documentID);
}
