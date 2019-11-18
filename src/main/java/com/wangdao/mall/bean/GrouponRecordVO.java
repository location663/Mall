package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GrouponRecordVO {
    private GoodsDO goods;
    private GrouponDO groupon;
    private GrouponRulesDO rules;
    private List<SubGrouponDTO> subGroupons;
}
