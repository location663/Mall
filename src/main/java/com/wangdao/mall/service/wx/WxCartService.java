package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.UserDO;

import java.util.List;
import java.util.Map;

public interface WxCartService {
    Integer add(String goodsId, String number, String productId, UserDO userDO);
    Map index(UserDO userDO);
    Integer fastAdd(String goodsId, String number, String productId, UserDO userDO);
    void checked(List<Integer> products, Boolean checked);
}
