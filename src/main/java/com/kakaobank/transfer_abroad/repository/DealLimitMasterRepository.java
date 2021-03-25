package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.DealLimitMaster;
import com.kakaobank.transfer_abroad.domain.DealLimitMasterPK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DealLimitMasterRepository extends JpaRepository<DealLimitMaster, DealLimitMasterPK> {

    @Query(value = "select ifnull(sum(cash_amt) ,0) transSuccessAmt\n" +
            "            from main_transaction_tran\n" +
            "            where deal_ymd = DATE_FORMAT(now(), '%Y%m%d')\n" +
            "            and acct_no = ?1\n" +
            "            and curr_code = ?2\n" +
            "            and bsns_rmrk_code in ('005','004')"
            , nativeQuery = true)
    Double findTransSuccessAmt(String acctNo , String currCode);

}

