package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.UserDO;

import java.util.Map;

public interface WxUserService {
    Map login();

    Map userIndex();

    void getRegCaptcha(String mobile);

    Map userRegister(UserDO userDO) throws Exception;
}
