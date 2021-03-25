package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransInfo {
    private String trnsBankCode;
    private String trnsBankAcctNo;
    private String trnsAcctNm;

    private String recvAddress;
    private String recvCity;
    private String recvNatnCode;
    private String recvStateCode;


}

