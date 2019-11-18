package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GrouponRecordVO {
    private GoodsDO goods;
    private GrouponDO groupon;
    private GrouponRulesDO rules;
    private List<SubGrouponDTO> subGroupons;

    public GrouponRecordVO() {
    }

    public GrouponRecordVO(GoodsDO goods, GrouponDO groupon, GrouponRulesDO rules, List<SubGrouponDTO> subGroupons) {
        this.goods = goods;
        this.groupon = groupon;
        this.rules = rules;
        this.subGroupons = subGroupons;
    }
}
