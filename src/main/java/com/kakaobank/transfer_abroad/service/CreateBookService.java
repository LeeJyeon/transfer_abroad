package com.kakaobank.transfer_abroad.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

import com.kakaobank.transfer_abroad.domain.*;
import com.kakaobank.transfer_abroad.errorWriter.ErrorWriter;
import com.kakaobank.transfer_abroad.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Service
@Slf4j
public class CreateBookService {


    private final MainTransactionTranRepository mainTransactionTranRepository;
    private final SendFepTranRepository sendFepTranRepository;
    private final ForeignTransactionTranRepository foreignTransactionTranRepository;
    private final AccountMasterRepository accountMasterRepository;
    private final AccountAmountMasterRepository accountAmountMasterRepository;

    private String strToday;
    private String IpAddress;

    @Autowired
    ErrorWriter ew;

    @Autowired
    public CreateBookService(MainTransactionTranRepository mainTransactionTranRepository, SendFepTranRepository sendFepTranRepository, ForeignTransactionTranRepository foreignTransactionTranRepository,  AccountMasterRepository accountMasterRepository, AccountAmountMasterRepository accountAmountMasterRepository) {
        this.mainTransactionTranRepository = mainTransactionTranRepository;
        this.sendFepTranRepository = sendFepTranRepository;
        this.foreignTransactionTranRepository = foreignTransactionTranRepository;
        this.accountMasterRepository = accountMasterRepository;
        this.accountAmountMasterRepository = accountAmountMasterRepository;
    }

    @PostConstruct
    private void baseValueMapping() throws UnknownHostException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar tmp = Calendar.getInstance();
        this.strToday = sdf.format(tmp.getTime());

        this.IpAddress = Inet4Address.getLocalHost().getHostAddress();
    }


    public boolean createTotalBook(String acctNo, String trnsBaseCode, String currCode, Double originTrnsAmt ,Double krwTrnsAmt, Double cmsn, TransInfo trnsInfo) {

        log.info("createTotalBook");

        createMainBook(acctNo, trnsBaseCode, currCode, krwTrnsAmt, cmsn ,trnsInfo);
        log.info("createMainBook success");

        createSubBook(acctNo, trnsBaseCode, trnsInfo ,mainTransactionTranRepository.findMaxDealNo(this.strToday, acctNo)  );
        log.info("createSubBook success");

        updateAccountAmountMaster(acctNo, -krwTrnsAmt - cmsn);
        log.info("updateAccountAmountMaster success");

        sendFep(acctNo, trnsBaseCode, trnsInfo, currCode, originTrnsAmt);
        log.info("sendFep success");

        return true;
    }


    public ResponseEntity<String> createMainBook(String acctNo, String trnsBaseCode, String currCode, Double trnsAmt, Double cmsn, TransInfo trnsInfo) {

        MainTransactionTran mainTransactionTran = new MainTransactionTran();

        String bsnsRmrkCode;
        if (trnsBaseCode.equals("01") == true) {
            bsnsRmrkCode = "005"; //WU빠른출금
        } else {
            bsnsRmrkCode = "004"; //해외출금
        }

        mainTransactionTran.setDealYmd(this.strToday);
        mainTransactionTran.setAcctNo(acctNo);
//        mainTransactionTran.setDealNo(0);
        mainTransactionTran.setCashAmt(trnsAmt);
        mainTransactionTran.setCmsnAmt(cmsn);
        mainTransactionTran.setExctAmt(mainTransactionTran.getCashAmt() + mainTransactionTran.getCmsnAmt());
        mainTransactionTran.setCurrCode(currCode);
        mainTransactionTran.setBsnsRmrkCode(bsnsRmrkCode);
        mainTransactionTran.setTrnsBankCode(trnsInfo.getTrnsBankCode());
        mainTransactionTran.setTrnsBankAcctNo(trnsInfo.getTrnsBankAcctNo());
        mainTransactionTran.setTrnsAcctNm(trnsInfo.getTrnsAcctNm());
        mainTransactionTran.setRegiId("test");
        mainTransactionTran.setRegiDate(LocalDateTime.now());
        mainTransactionTran.setRegiIp(IpAddress);
        mainTransactionTran.setModiId("test");
        mainTransactionTran.setModiDate(LocalDateTime.now());
        mainTransactionTran.setModiIp(IpAddress);

        System.out.println("mainTransactionTran = " + mainTransactionTran);


        try {
            /* create Book */
            mainTransactionTranRepository.save(mainTransactionTran);
        } catch (Exception e) {
            log.error(acctNo + "원장작성실패");
            return new ResponseEntity<String>(ew.getMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("정상", HttpStatus.OK);


    }

    public ResponseEntity<String> createSubBook(String acctNo, String trnsBaseCode, TransInfo trnsInfo , Integer dealNo) {
        ForeignTransactionTran foreignTransactionTran = new ForeignTransactionTran();

        Optional<AccountMaster> accountMaster = accountMasterRepository.findById(acctNo);


        foreignTransactionTran.setDealYmd(strToday);
        foreignTransactionTran.setAcctNo(acctNo);
        foreignTransactionTran.setDealNo(dealNo);
        foreignTransactionTran.setTrnsBaseCode(trnsBaseCode);
        foreignTransactionTran.setSendNm(accountMaster.get().getAcctNm());
        foreignTransactionTran.setRecvNm(trnsInfo.getTrnsAcctNm());
        foreignTransactionTran.setRecvAddress(trnsInfo.getRecvAddress());
        foreignTransactionTran.setRecvCity(trnsInfo.getRecvCity());
        foreignTransactionTran.setRecvNatnCode(trnsInfo.getRecvNatnCode());
        foreignTransactionTran.setRecvStateCode(trnsInfo.getRecvStateCode());
        foreignTransactionTran.setRegiId("test");
        foreignTransactionTran.setRegiDate(LocalDateTime.now());
        foreignTransactionTran.setRegiIp(IpAddress);
        foreignTransactionTran.setModiId("test");
        foreignTransactionTran.setModiDate(LocalDateTime.now());
        foreignTransactionTran.setModiIp(IpAddress);

        try {
            /* create Sub Book */
            foreignTransactionTranRepository.save(foreignTransactionTran);
        } catch (Exception e) {
            log.error(acctNo + "보조 원장 작성실패");
            return new ResponseEntity<String>(ew.getMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public ResponseEntity<String> updateAccountAmountMaster(String acctNo, Double trnsAmount) {

        Optional<AccountAmountMaster> accountAmountMaster = accountAmountMasterRepository.findById(acctNo);

        try {
            accountAmountMaster.ifPresent(find -> {
                find.setCashAmt(accountAmountMaster.get().getCashAmt() + trnsAmount);
                find.setModiDate(LocalDateTime.now());
                find.setModiIp(IpAddress);
                AccountAmountMaster newAccountAmountMaster = accountAmountMasterRepository.save(find);
            });
        } catch (Exception e) {
            log.error(acctNo + "잔고테이블 수정 에러");
            return new ResponseEntity<String>(ew.getMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public ResponseEntity<String> sendFep(String acctNo, String trnsBaseCode, TransInfo trnsInfo, String currCode, Double trnsAmt) {

        Optional<AccountMaster> accountMaster = accountMasterRepository.findById(acctNo);

        SendFepTran sendFepTran = new SendFepTran();

        sendFepTran.setProcYmd(strToday);
//        sendFepTran.setProcSeq(0);
        sendFepTran.setTrnsBaseCode(trnsBaseCode);
        sendFepTran.setFepComCntt(setComcntt(acctNo, trnsInfo, currCode, trnsAmt, accountMaster));

        try {
            /* send Fep*/
            sendFepTranRepository.save(sendFepTran);
        } catch (Exception e) {
            log.error(acctNo + "FEP전송실패");
            return new ResponseEntity<String>(ew.getMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String setComcntt(String acctNo, TransInfo trnsInfo, String currCode, Double trnsAmt, Optional<AccountMaster> accountMaster) {
        /*
         * 보내는 은행 코드
         * 보내는 은행 계좌번호
         * 보내는 은행 계좌명
         *
         * 받는 은행 코드
         * 받는 은행 계좌번호
         * 받는 은행 계좌명
         * 받는 계좌 주소정보
         *
         * 송금 통화코드
         * 송금액
         * */

        return
                String.format("%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s%10.4f",
                        "KAKAOBANKCODE", acctNo, accountMaster.get().getAcctNm(),
                        trnsInfo.getTrnsBankCode(),
                        trnsInfo.getTrnsBankAcctNo(),
                        trnsInfo.getTrnsAcctNm(),
                        trnsInfo.getRecvAddress(),
                        trnsInfo.getRecvCity(),
                        trnsInfo.getRecvAddress(),
                        trnsInfo.getRecvStateCode(),

                        currCode, trnsAmt
                );
    }
}