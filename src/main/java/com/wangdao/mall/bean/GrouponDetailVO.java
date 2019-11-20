package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GrouponDetailVO {
    private GrouponDO groupon;
    private Integer linkGrouponId;
    private List<UserDO> joiners;
    private OrderDO orderInfo;
    private List<OrderGoodsDO> orderGoods;
    private List<GoodsDO> goodsList;
    private GrouponRulesDO rules;
    private Double actualPrice;
    private Object creator;
    private Integer id;
    private Boolean isCreator;
    private Integer joinerCount;
    private Integer orderId;
    private String orderSn;
    private String orderStatusText;
}
