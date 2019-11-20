package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class OrderVO {

    private Integer id;

    private String orderStatusText;

    private Boolean isGroupin;

    private String orderSn;

    private Double actualPrice;

    private List<OrderGoodsDO> goodsList;

    private OrderHandleOption handleOption;
}
