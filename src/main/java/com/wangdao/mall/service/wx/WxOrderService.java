package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.BaseReqVo;

import java.util.Map;

public interface WxOrderService {

    BaseReqVo submitOrder(Map<String, Object> map);

    BaseReqVo getOrderList(Integer showType, Integer page, Integer size);
}
