package com.kakaobank.transfer_abroad.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class LogColumn {

    private String regiId;
    private LocalDateTime regiDate;
    private String regiIp;
    private String modiId;
    private LocalDateTime modiDate;
    private String modiIp;
}
