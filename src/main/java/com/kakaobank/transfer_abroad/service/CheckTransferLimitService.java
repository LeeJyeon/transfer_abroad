package com.kakaobank.transfer_abroad.service;

import com.kakaobank.transfer_abroad.domain.AccountMaster;
import com.kakaobank.transfer_abroad.domain.DealLimitMaster;
import com.kakaobank.transfer_abroad.domain.DealLimitMasterPK;
import com.kakaobank.transfer_abroad.errorWriter.ErrorWriter;
import com.kakaobank.transfer_abroad.repository.AccountMasterRepository;
import com.kakaobank.transfer_abroad.repository.DealLimitMasterRepository;
import com.kakaobank.transfer_abroad.repository.ExchangeRateTranRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CheckTransferLimitService {

    private final AccountMasterRepository accountMasterRepository;
    private final DealLimitMasterRepository dealLimitMasterRepository;
    private final ExchangeRateTranRepository exchangeRateTranRepository;

    @Autowired
    ErrorWriter ew;

    public ResponseEntity<String> checkExcptLimitIsOk(String acctNo, String trnsBaseCode, String currCode, Double trnsAmt) {
        String excptYn = accountMasterRepository.findTransferExcptYn(acctNo);

        DealLimitMasterPK dealLimitMasterPK = new DealLimitMasterPK();
        dealLimitMasterPK.setTrnsBaseCode(trnsBaseCode);
        dealLimitMasterPK.setCurrCode(currCode);
        dealLimitMasterPK.setExcptYn(excptYn);

        Double limitAmt = dealLimitMasterRepository.findById(dealLimitMasterPK).get().getTrnsLimAmt();
        Double alreadyTransAmt = dealLimitMasterRepository.findTransSuccessAmt(acctNo, currCode);
        Integer exchangeRate = exchangeRateTranRepository.findRate(currCode).getSellRate();

        if (limitAmt - alreadyTransAmt / exchangeRate  < trnsAmt) {
            log.error(acctNo + "한도부족" + "한도 [" + limitAmt + "] 기출금액 [" + alreadyTransAmt / exchangeRate + "] 이체시도금액 [" + trnsAmt + "]");
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }


}
