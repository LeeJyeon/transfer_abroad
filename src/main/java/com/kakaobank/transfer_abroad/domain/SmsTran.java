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
@IdClass(SmsTranPK.class)
public class SmsTran  {
    @Id
    private String procYmd;
    @Id
    private String acctNo;
    @Id
    private Integer procSeq;

    private String tellNo;
    private String msgCntt;
}
