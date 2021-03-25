package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountMaster extends LogColumn{
    @Id
    private String acctNo;

    private String password;
    private String acctNm;
    private String acctMangBaseCode;
    private String livingYn;
    private String stayYn;
    private String tellNo;

}
