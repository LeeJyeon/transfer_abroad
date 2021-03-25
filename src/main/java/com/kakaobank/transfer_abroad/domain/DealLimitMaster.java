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
@IdClass(DealLimitMasterPK.class)
@ToString
public class DealLimitMaster extends LogColumn{

    @Id
    private String trnsBaseCode;
    @Id
    private String currCode;
    @Id
    private String excptYn;

    private Double trnsLimAmt;
}


