package com.wangdao.mall.service;


import com.wangdao.mall.bean.BaseReqVo;

import java.util.Map;

public interface ConfigService {

    BaseReqVo<Map<String, Object>> getExpressConfig();

    BaseReqVo setExpressConfig(Map<String, Object> map);

    BaseReqVo<Map<String, Object>> getMallConfig();

    BaseReqVo setMallConfig(Map<String, Object> map);

    BaseReqVo<Map<String, Object>> getOrderConfig();

    BaseReqVo setOrderConfig(Map<String, Object> map);

    BaseReqVo<Map<String, Object>> getWxConfig();

    BaseReqVo setWxConfig(Map<String, Object> map);
}
