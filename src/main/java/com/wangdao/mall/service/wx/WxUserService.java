package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.exception.WxException;

import java.util.Map;

public interface WxUserService {
    Map login(String lastLoginIp);

    Map userIndex();

    void getRegCaptcha(String mobile);

    Map userRegister(UserDO userDO) throws Exception;

    int resetPassword(String mobile, String code, String password) throws WxException;

    void loginnByWeixin(Map userInfoMap, String code);
}
