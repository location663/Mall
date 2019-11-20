package com.wangdao.mall.service.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangdao.mall.bean.BaseReqVo;

import java.util.Map;

public interface WxOrderService {

    BaseReqVo submitOrder(Map<String, Object> map) throws JsonProcessingException;
}
