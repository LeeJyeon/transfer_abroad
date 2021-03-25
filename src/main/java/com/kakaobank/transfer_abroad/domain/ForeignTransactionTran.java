package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Setter
@Getter
@NoArgsConstructor
@IdClass(ForeignTransactionTranPK.class)
public class ForeignTransactionTran extends LogColumn {
    @Id
    private String dealYmd;
    @Id
    private String acctNo;
    @Id
    private Integer dealNo;

    private String trnsBaseCode;
    private String sendNm;
    private String recvNm;
    private String recvAddress;
    private String recvCity;
    private String recvNatnCode;
    private String recvStateCode;
}

