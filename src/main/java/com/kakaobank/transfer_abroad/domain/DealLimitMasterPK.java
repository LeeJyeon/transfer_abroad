package com.kakaobank.transfer_abroad.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Getter
@Setter
public class DealLimitMasterPK implements Serializable {

    private String trnsBaseCode;
    private String currCode;
    private String excptYn;
}
