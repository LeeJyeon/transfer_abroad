package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.AccountAmountMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountAmountMasterRepository extends JpaRepository<AccountAmountMaster,String> {

}
