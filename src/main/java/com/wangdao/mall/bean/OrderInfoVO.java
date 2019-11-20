package com.wangdao.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVO {

    private Integer id;

    private String consignee;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date addTime;

    private String orderSn;

    private Double actualPrice;

    private String mobile;

    private String orderStatusText;

    private Double goodsPrice;

    private Double couponPrice;

    private Double freightPrice;

    private OrderHandleOption handleOption;

}
