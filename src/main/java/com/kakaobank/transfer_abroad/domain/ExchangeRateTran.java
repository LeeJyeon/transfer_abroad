package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@IdClass(ExchangeRateTranPK.class)
public class ExchangeRateTran extends LogColumn  {

    @Id
    private String procYmd;
    @Id
    private Integer procSeq;
    @Id
    private String currCode;

    private Integer buyRate;
    private Integer sellRate;
    private Integer baseRate;

}
