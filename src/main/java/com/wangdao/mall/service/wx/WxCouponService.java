package com.wangdao.mall.service.wx;

import java.util.HashMap;
import java.util.Map;

public interface WxCouponService {

    HashMap<String, Object> queryCouponList(Integer page, Integer size);


    int couponReceive(Map map);
}
