package com.kakaobank.transfer_abroad.domain;

import com.mysql.cj.log.Log;
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
@IdClass(DealFeeMasterPK.class)
public class DealFeeMaster extends LogColumn {
    @Id
    private String trnsBaseCode;
    @Id
    private Integer sectionBaseAmt;

    private String currCode;
    private Integer feeAmt;
}


