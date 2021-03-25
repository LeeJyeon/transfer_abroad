package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.ExchangeRateTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateTranRepository extends JpaRepository<ExchangeRateTran,String> {

    @Query(value = "select *\n" +
            "from exchange_rate_tran\n" +
            "where proc_ymd = DATE_FORMAT(now(), '%Y%m%d')\n" +
            "and curr_code = ?1\n" +
            "and proc_seq = (\n" +
            "  select max(proc_seq)\n" +
            "  from exchange_rate_tran\n" +
            "  where proc_ymd = DATE_FORMAT(now(), '%Y%m%d')\n" +
            "\tand curr_code = ?1\n" +
            "  group by proc_ymd\n" +
            "  )"
            , nativeQuery = true)
    ExchangeRateTran findRate(String currCode);

}
