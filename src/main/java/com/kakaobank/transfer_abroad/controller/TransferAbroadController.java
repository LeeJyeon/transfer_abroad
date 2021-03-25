package com.kakaobank.transfer_abroad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobank.transfer_abroad.domain.TransferAbroadInput;
import com.kakaobank.transfer_abroad.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class TransferAbroadController {
    private final CalculateAmountService calculateAmountService;
    private final CheckAcctStateService checkAcctStateService;
    private final CheckTransferLimitService checkTransferLimitService;
    private final CreateBookService createBookService;
    private final SendSmsService sendSmsService;

    @Autowired
    public TransferAbroadController(CalculateAmountService calculateAmountService
            , CheckAcctStateService checkAcctStateService
            , CheckTransferLimitService checkTransferLimitService
            , CreateBookService createBookService
            , SendSmsService sendSmsService, ObjectMapper mapper) {
        this.calculateAmountService = calculateAmountService;
        this.checkAcctStateService = checkAcctStateService;
        this.checkTransferLimitService = checkTransferLimitService;
        this.createBookService = createBookService;
        this.sendSmsService = sendSmsService;
    }

    @PostMapping("/transferAbroad")
    public ResponseEntity<String> transfer(@RequestBody TransferAbroadInput transferAbroadInput){


        /*비밀번호검증*/
        checkAcctStateService.checkPasswordIsOk(transferAbroadInput.getAcctNo(), transferAbroadInput.getPassword());
        log.info("비밀번호검증 완료");

        /*계좌상태검증*/
        checkAcctStateService.checkAcctStateIsOk(transferAbroadInput.getAcctNo());
        log.info("계좌상태검증 완료");

        /*잔액검증*/
        calculateAmountService.checkTrnsAmtIsOk(transferAbroadInput.getAcctNo()
                , transferAbroadInput.getCurrCode()
                , transferAbroadInput.getTrnsBaseCode()
                , transferAbroadInput.getTrnsAmt()
        );
        log.info("잔액검증 완료");


        /*이체한도검증*/
        checkTransferLimitService.checkExcptLimitIsOk(transferAbroadInput.getAcctNo()
                , transferAbroadInput.getTrnsBaseCode()
                , transferAbroadInput.getCurrCode()
                , transferAbroadInput.getTrnsAmt()
        );
        log.info("이체한도검증 완료");

        /* 수수료 계산 */
        Double tmpCmsn =
                calculateAmountService.calFeeKrw(transferAbroadInput.getCurrCode()
                        , transferAbroadInput.getTrnsBaseCode()
                        , transferAbroadInput.getTrnsAmt()
                );
        log.info("수수료 계산 완료");


        /* 환전 */
        Double krwTrnsAmt = calculateAmountService.calTrnsKrw(transferAbroadInput.getCurrCode()
                , transferAbroadInput.getTrnsBaseCode()
                , transferAbroadInput.getTrnsAmt()
        );
        log.info("환전 완료");


        /* 원장작성 */
        log.info("원장작성시작");
        createBookService.createTotalBook(transferAbroadInput.getAcctNo()
                , transferAbroadInput.getTrnsBaseCode()
                , transferAbroadInput.getCurrCode()
                , transferAbroadInput.getTrnsAmt()
                , krwTrnsAmt
                , tmpCmsn
                , transferAbroadInput.getTrnsInfo()
        );
        log.info("원장작성 완료");


        /* sms전송 */
        sendSmsService.sendSms(transferAbroadInput.getAcctNo(), transferAbroadInput.getTrnsAmt());

        return new ResponseEntity<String>("이체완료", HttpStatus.OK);

    }

}
