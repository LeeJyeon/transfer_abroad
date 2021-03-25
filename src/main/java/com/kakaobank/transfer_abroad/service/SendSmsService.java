package com.kakaobank.transfer_abroad.service;

import com.kakaobank.transfer_abroad.domain.AccountMaster;
import com.kakaobank.transfer_abroad.domain.SmsTran;
import com.kakaobank.transfer_abroad.errorWriter.ErrorWriter;
import com.kakaobank.transfer_abroad.repository.AccountMasterRepository;
import com.kakaobank.transfer_abroad.repository.SmsTranRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class SendSmsService {
    private final SmsTranRepository smsTranRepository;
    private final AccountMasterRepository accountMasterRepository;

    private String strToday;
    private String IpAddress;

    @Autowired
    ErrorWriter ew;

    @Autowired
    public SendSmsService(SmsTranRepository smsTranRepository, AccountMasterRepository accountMasterRepository) {
        this.smsTranRepository = smsTranRepository;
        this.accountMasterRepository = accountMasterRepository;
    }

    @PostConstruct
    private void baseValueMapping() throws UnknownHostException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar tmp = Calendar.getInstance();
        this.strToday = sdf.format(tmp.getTime());
        this.IpAddress = Inet4Address.getLocalHost().getHostAddress();
    }

    public ResponseEntity<String> sendSms(String acctNo, Double trnsAmt) {

        SmsTran smsTran = new SmsTran();

        Optional<AccountMaster> accountMaster = accountMasterRepository.findById(acctNo);

        smsTran.setProcYmd(strToday);
        smsTran.setAcctNo(acctNo);
//        smsTran.setProcSeq(0);
        smsTran.setTellNo(accountMaster.get().getTellNo());
        smsTran.setMsgCntt(accountMaster.get().getAcctNm() + "고객님 계좌 "
                + accountMaster.get().getAcctNo() + "에서 해외송금 거래가 발생했습니다."
                + "외화 : "
                + trnsAmt
        );

        try {
            smsTranRepository.save(smsTran);
        } catch (Exception e) {
            log.error(acctNo + "sms fail");
            return new ResponseEntity<String>(ew.getMessage(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
