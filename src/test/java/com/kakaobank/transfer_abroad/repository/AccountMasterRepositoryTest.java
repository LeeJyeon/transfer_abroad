package com.kakaobank.transfer_abroad.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountMasterRepositoryTest {

    @Autowired
    AccountMasterRepository accountMasterRepository;

    @Test
    void Queury(){
        String yn = accountMasterRepository.findTransferExcptYn("123456789");
        System.out.println("yn = " + yn);
    }



}