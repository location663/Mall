package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CheckoutDataBean;
import com.wangdao.mall.bean.ReceiveCartDo;
import com.wangdao.mall.bean.UserDO;

import java.util.List;
import java.util.Map;

public interface WxCartService {
    Integer add(Integer goodsId, Integer number, Integer productId, UserDO userDO);
    Map index(UserDO userDO);
    Integer fastAdd(Integer goodsId, Integer number, Integer productId, UserDO userDO);
    void checked(List<Integer> products, Boolean checked);
    CheckoutDataBean checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId,UserDO userDo);
    void update(ReceiveCartDo receiveCartDo, UserDO userDO);
}
