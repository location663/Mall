package com.wangdao.mall.service.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WxCouponService {

    HashMap<String, Object> queryCouponList(Integer page, Integer size);

    int couponReceive(Integer couponId);

    Integer couponExchange(String code);

    HashMap<String, Object> couponMylist(Short status, Integer page, Integer size);

    List<Map> couponSelectlist(Integer cartId, Integer grouponRulesId);
}
