package com.kakaobank.transfer_abroad.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Getter
@Setter
public class ExchangeRateTranPK implements Serializable {
    private String procYmd;
    private Integer procSeq;
    private String currCode;
}
