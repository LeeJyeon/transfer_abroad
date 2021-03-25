package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.SmsTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsTranRepository extends JpaRepository<SmsTran,String> {
}
