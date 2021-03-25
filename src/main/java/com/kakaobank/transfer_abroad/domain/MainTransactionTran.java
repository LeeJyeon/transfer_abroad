package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@IdClass(MainTransactionTranPK.class)
@ToString
public class MainTransactionTran extends LogColumn  {

    @Id
    private String dealYmd;
    @Id
    private String acctNo;
    @Id
    private Integer dealNo;

    private Double cashAmt;
    private Double cmsnAmt;
    private Double exctAmt;
    private String currCode;
    private String bsnsRmrkCode;
    private String trnsBankCode;
    private String trnsBankAcctNo;
    private String trnsAcctNm;
}
