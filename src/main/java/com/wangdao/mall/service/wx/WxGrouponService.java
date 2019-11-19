package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.GrouponDetailVO;
import com.wangdao.mall.bean.RequestPageDTO;

import java.util.Map;

public interface WxGrouponService {
    Map<String, Object> listGroupon(RequestPageDTO pageDTO);

    GrouponDetailVO selectById(Integer grouponId);
}
