package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.AccountMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMasterRepository extends JpaRepository<AccountMaster,String> {

    @Query(value = "select case when living_yn = 'Y' or  stay_yn = 'Y' then 'Y' else 'N' end excpt_yn\n" +
            "from account_master\n" +
            "where acct_no = ?1"
            , nativeQuery = true)
    String findTransferExcptYn(String acct_no);
}
