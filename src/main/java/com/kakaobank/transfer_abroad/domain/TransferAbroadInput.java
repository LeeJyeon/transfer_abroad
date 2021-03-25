package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferAbroadInput {
    private String acctNo;
    private String password;
    private String currCode;
    private String trnsBaseCode;
    private Double trnsAmt;

    TransInfo trnsInfo;
}

