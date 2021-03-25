package com.kakaobank.transfer_abroad.service;

import com.kakaobank.transfer_abroad.domain.AccountAmountMaster;
import com.kakaobank.transfer_abroad.domain.ExchangeRateTran;
import com.kakaobank.transfer_abroad.errorWriter.ErrorWriter;
import com.kakaobank.transfer_abroad.repository.AccountAmountMasterRepository;
import com.kakaobank.transfer_abroad.repository.DealFeeMasterRepository;
import com.kakaobank.transfer_abroad.repository.ExchangeRateTranRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CalculateAmountService {

    private final DealFeeMasterRepository dealFeeMasterRepository;
    private final ExchangeRateTranRepository exchangeRateTranRepository;
    private final AccountAmountMasterRepository accountAmountMasterRepository;

    @Autowired
    ErrorWriter ew;

    public ResponseEntity<String> checkTrnsAmtIsOk(String acctNo ,String currCode , String trnsBaseCode, Double trnsAmt ){

        Optional<AccountAmountMaster> accountAmountMaster = accountAmountMasterRepository.findById(acctNo);

        Double presentAmt = accountAmountMaster.get().getCashAmt();
        Double attemptAmt = calFeeKrw(currCode,trnsBaseCode,trnsAmt) + calTrnsKrw(currCode, trnsBaseCode, trnsAmt);

        if( attemptAmt > presentAmt ){
            log.error(acctNo + "금액부족");
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public Double calFeeKrw(String currCode, String trnsBaseCode, Double trnsAmt) {
        try {
            return dealFeeMasterRepository.findTranFeeKrw(currCode, trnsBaseCode, trnsAmt);
        } catch (Exception e) {
            log.error(currCode +" " + trnsBaseCode + " " + trnsAmt + " 수수료계산 이상");
            return 0.0;
        }
    }

    public Double calTrnsKrw(String currCode , String trnsBaseCode, Double trnsAmt){
        ExchangeRateTran exchangeRate = exchangeRateTranRepository.findRate(currCode);

        if( trnsBaseCode.equals("01") ){ //WU빠른송금
            return trnsAmt * exchangeRate.getSellRate();
        }else { //일반송금
            return trnsAmt * (exchangeRate.getSellRate() + exchangeRate.getBaseRate()) / 2;
        }
    }


}
