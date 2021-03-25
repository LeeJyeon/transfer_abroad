package com.kakaobank.transfer_abroad.controller;
import com.kakaobank.transfer_abroad.domain.TransInfo;

import com.kakaobank.transfer_abroad.domain.TransferAbroadInput;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Commit
class TransferAbroadControllerTest {

    @Autowired
    TransferAbroadController transferAbroadController;

    @Test
    @Commit
    void totalTest(){


        /*
        *   1) 고객 수는 현재 천 만명입니다.
            2) 일 평균 해외계좌송금 거래량이 1000건입니다.
            3) 사용 통화는 USD로 통일하며, 환율은 USD 1불당 매매기준율(1100원), 매입(1000원), 매도(1200원)으로 통일합니다.
            4) 송금인 영문명 INDIVIDUAL SENDER
                수취인 영문명 CORPORATION BENE, 수취인주소 FIFTH AVENUE, 도시 MANHATTAN, 주 NY,
                수취은행코드 123456789, 계좌번호 987654321 로 명시합니다.
    * */
        TransInfo transInfo = new TransInfo();

        transInfo.setTrnsBankCode("123456789");
        transInfo.setTrnsBankAcctNo("987654321");
        transInfo.setTrnsAcctNm("CORPORATION BENE");
        transInfo.setRecvAddress("FIFTH AVENUE");
        transInfo.setRecvCity("MANHATTAN");
        transInfo.setRecvNatnCode("USA");
        transInfo.setRecvStateCode("NY");


        TransferAbroadInput transferAbroadInput = new TransferAbroadInput();

        transferAbroadInput.setAcctNo("27024211370");
        transferAbroadInput.setPassword("1234");
        transferAbroadInput.setCurrCode("USD");
        transferAbroadInput.setTrnsBaseCode("01");
        transferAbroadInput.setTrnsAmt(1.0D);
        transferAbroadInput.setTrnsInfo(transInfo);


        transferAbroadController.transfer(transferAbroadInput);

    }

}