package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.DealFeeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DealFeeMasterRepository extends JpaRepository<DealFeeMaster,String> {

    @Query(value = "select max(a.fee_amt * ifnull(b.sell_rate,1)) fee_krw\n" +
            "from deal_fee_master as a\n" +
            "left outer join (\n" +
            "\t\tselect *\n" +
            "\t\tfrom exchange_rate_tran\n" +
            "\t\twhere proc_ymd = DATE_FORMAT(now(), '%Y%m%d')\n" +
            "\t\tand curr_code = ?1\n" +
            "\t\tand proc_seq = (\n" +
            "\t\t  select max(proc_seq)\n" +
            "\t\t  from exchange_rate_tran\n" +
            "\t\t  where proc_ymd = DATE_FORMAT(now(), '%Y%m%d')\n" +
            "\t\t\tand curr_code = ?1\n" +
            "\t\t  group by proc_ymd\n" +
            "\t\t  )\n" +
            "      ) as b\n" +
            "on a.curr_code = b.curr_code\n" +
            "where a.trns_base_code = ?2\n" +
            "  and a.section_base_amt < ?3"
            , nativeQuery = true)
    Double findTranFeeKrw(String currCode , String trnsBaseCode , Double trnsAmt);
}
