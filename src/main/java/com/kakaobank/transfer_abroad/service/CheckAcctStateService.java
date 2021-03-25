package com.kakaobank.transfer_abroad.service;

import com.kakaobank.transfer_abroad.domain.AccountMaster;
import com.kakaobank.transfer_abroad.errorWriter.ErrorWriter;
import com.kakaobank.transfer_abroad.repository.AccountMasterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CheckAcctStateService {
    private final AccountMasterRepository accountMasterRepository;

    @Autowired
    ErrorWriter ew;

    public ResponseEntity<String> checkPasswordIsOk(String acctNo, String password) {
        Optional<AccountMaster> result = accountMasterRepository.findById(acctNo);

        if( result.get().getPassword().equals(password) == false ){
            log.error(acctNo + " " + result.get().getPassword() + " " + password + "불일치");
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public ResponseEntity<String> checkAcctStateIsOk(String acctNo){
        Optional<AccountMaster> result = accountMasterRepository.findById(acctNo);

        if( result.get().getAcctMangBaseCode().equals("00") == false ){
            log.error(result.get().getAcctMangBaseCode()  + "계좌상태이상");
            return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
