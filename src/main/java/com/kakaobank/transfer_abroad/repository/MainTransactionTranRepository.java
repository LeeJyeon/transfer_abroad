package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.ExchangeRateTran;
import com.kakaobank.transfer_abroad.domain.MainTransactionTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MainTransactionTranRepository extends JpaRepository<MainTransactionTran,String> {

    @Query(value = "select ifnull(max(deal_no),1) dealNo\n" +
            "from main_transaction_tran\n" +
            "where deal_ymd = ?1\n" +
            "and acct_no = ?2"
            , nativeQuery = true)
    Integer findMaxDealNo(String dealYmd , String acctNo);
}
