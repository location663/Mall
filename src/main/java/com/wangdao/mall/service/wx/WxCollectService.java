package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CollectDO;

import java.util.HashMap;

public interface WxCollectService {
    HashMap<String, Object> queryCollectList(Integer type, Integer page, Integer size);

    HashMap<String, Object> collectAddordelete(CollectDO collectDO);
}
