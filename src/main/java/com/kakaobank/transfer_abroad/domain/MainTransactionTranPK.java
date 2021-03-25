package com.kakaobank.transfer_abroad.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
@Data
@Getter
@Setter
public class MainTransactionTranPK implements Serializable {

    private String dealYmd;
    private String acctNo;
    private Integer dealNo;

}
