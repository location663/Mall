package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.LogDO;

public interface LogService {

    void insertLog(LogDO logDO);

    Integer selectLastInsertId();

    String searchLastadmin(Integer id);
}
