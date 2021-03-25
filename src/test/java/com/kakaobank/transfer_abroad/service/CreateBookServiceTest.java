package com.kakaobank.transfer_abroad.service;

import com.kakaobank.transfer_abroad.domain.TransInfo;
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
class CreateBookServiceTest {

    @Autowired
    CreateBookService createBookService;
    @Autowired
    CalculateAmountService calculateAmountService;

    @Test
    public void createBookTest() {

        TransInfo transInfo = new TransInfo();
        transInfo.setTrnsBankCode("278");
        transInfo.setTrnsBankAcctNo("27024211370");
        transInfo.setTrnsAcctNm("LJH");
        transInfo.setRecvAddress("Address");
        transInfo.setRecvCity("Seoul");
        transInfo.setRecvNatnCode("USA");
        transInfo.setRecvStateCode("NY");

    }


}