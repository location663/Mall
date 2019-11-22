package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.BaseReqVo;

import java.util.Map;

public interface DashboardService {
    Map countAll();

    BaseReqVo profilePassword(Map map);
}
