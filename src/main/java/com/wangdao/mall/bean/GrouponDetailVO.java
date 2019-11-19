package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GrouponDetailVO {
    private GrouponDO groupon;
    private Integer linkGrouponId;
    private List<UserDO> joiners;
    private OrderDO orderInfo;
    private List<GoodsDO> orderGoods;
}
