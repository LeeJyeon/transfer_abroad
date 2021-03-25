package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.SendFepTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendFepTranRepository extends JpaRepository<SendFepTran,String> {
}
