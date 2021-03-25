package com.kakaobank.transfer_abroad.repository;

import com.kakaobank.transfer_abroad.domain.DealLimitMaster;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DealLimitMasterRepositoryTest {

    @Autowired
    DealLimitMasterRepository dealLimitMasterRepository;

    @Test
    void baseQuery() {
        List<DealLimitMaster> result = dealLimitMasterRepository.findAll();
        System.out.println();
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        System.out.println(result.get(2));
        System.out.println(result.get(3));

    }
}