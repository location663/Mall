package com.wangdao.mall.bean;

import lombok.Data;

@Data
public class ExpressInfoVO {
    private String shipperName;
    private TraceVO Traces;
    private String logisticCode;
}
