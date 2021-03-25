package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.ForeignTransactionTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeignTransactionTranRepository extends JpaRepository<ForeignTransactionTran,String> {
}
